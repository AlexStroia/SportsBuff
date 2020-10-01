package com.buffup.sdk

import android.animation.Animator
import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.buffup.sdk.adapter.BuffAdapter
import com.buffup.sdk.adapter.OnAnswerSelected
import com.buffup.sdk.adapter.OnCloseSelected
import com.buffup.sdk.model.BuffUiModel
import com.buffup.sdk.utils.setImage


private const val ANIM_DURATION = 1000L

class BuffView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: BuffViewBinding = BuffViewBinding.inflate(LayoutInflater.from(context))

    private val _question = MutableLiveData<BuffUiModel.Question>()
    val question: LiveData<BuffUiModel.Question> get() = _question

    private val _author = MutableLiveData<BuffUiModel.Author>()
    val author: LiveData<BuffUiModel.Author> get() = _author

    private lateinit var timer: CountDownTimer
    private var currentProgressBarProgress = 0

    private var timeToShow = 0L

    private val adapter by lazy {
        BuffAdapter(object : OnAnswerSelected {
            override fun invoke(uiModel: BuffUiModel) {
            }
        }, object : OnCloseSelected {
            override fun invoke() {
            }
        })
    }

    init {
        addView(binding.root)
        with(binding) {
            rvQuestions.adapter = adapter
            //  container.invalidate()

            buffSender.buffClose.setOnClickListener {
                timer.cancel()
                handleRightToLeftAnimation()
            }
        }

        author.observeForever {
            binding.buffSender.senderName.text = it.firstName
            binding.buffSender.senderImage.setImage(it.image)
        }

        question.observeForever {
            binding.buffQuestion.questionText.text = it.text
        }
    }

    fun setData(uiModel: List<BuffUiModel>) {
        val answers = uiModel.filterIsInstance(BuffUiModel.Answer::class.java)
        val author = uiModel.filterIsInstance(BuffUiModel.Author::class.java).first()
        val question = uiModel.filterIsInstance(BuffUiModel.Question::class.java).first()
        _author.value = author
        _question.value = question
        adapter.submitList(answers.toMutableList())

        handleLeftToRightAnimation()
    }

    fun setTime(timeToShow: Long) {
        this.timeToShow = timeToShow * 1000
    }

    private fun setProgressBar() {
        timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                print("TimeToShow is $timeToShow")
                val progress = timeToShow - (millisUntilFinished / 1000)
                with(binding.buffQuestion) {
                    val seconds: Long = millisUntilFinished / 1000
                    questionTime.text = (seconds).toString()
                    binding.buffQuestion.questionTimeProgress.progress = (progress.toInt())
                }
            }

            override fun onFinish() {
                handleRightToLeftAnimation()
                timer.cancel()
            }
        }.start()
    }

    private fun handleLeftToRightAnimation() {
        with(binding) {
            container.animate().setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    setProgressBar()
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            }).translationX(0f)
                .setInterpolator(AccelerateDecelerateInterpolator()).duration = ANIM_DURATION
        }
    }

    private fun handleRightToLeftAnimation() {
        binding.container.animate().translationX(-binding.container.width.toFloat())
            .setInterpolator(AccelerateDecelerateInterpolator()).duration = ANIM_DURATION
    }
}