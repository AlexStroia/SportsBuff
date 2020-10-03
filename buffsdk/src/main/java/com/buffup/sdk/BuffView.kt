package com.buffup.sdk

import android.animation.Animator
import android.content.Context
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.buffup.sdk.adapter.BuffAdapter
import com.buffup.sdk.adapter.OnAnswerSelected
import com.buffup.sdk.model.AnswerUiModel
import com.buffup.sdk.model.AuthorUiModel
import com.buffup.sdk.model.QuestionUiModel
import com.buffup.sdk.utils.replace
import com.buffup.sdk.utils.setImage
import kotlinx.android.synthetic.main.buff_question.view.*


private const val ANIM_DURATION = 1000L
private const val ITEM_TAP_DELAY = 2000L

private const val TIMER_INTERVAL = 1000L

class BuffView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: BuffViewBinding = BuffViewBinding.inflate(LayoutInflater.from(context))

    private val _question = MutableLiveData<QuestionUiModel>()
    val question: LiveData<QuestionUiModel> get() = _question

    private val _author = MutableLiveData<AuthorUiModel>()
    val author: LiveData<AuthorUiModel> get() = _author

    private val _answers = MutableLiveData<List<AnswerUiModel>>()
    val answers: LiveData<List<AnswerUiModel>> get() = _answers

    private lateinit var timer: CountDownTimer

    private var timeToShow = 0L

    private var isLeftToRightAnimation = false

    private var isRecyclerViewTouchEnabled = true

    private val adapter by lazy {
        BuffAdapter(object : OnAnswerSelected {
            override fun invoke(uiModel: AnswerUiModel) {
                if (isRecyclerViewTouchEnabled) {
                    isRecyclerViewTouchEnabled = false
                    val highlightedAnswer = uiModel.copy(shouldAnimateOverlay = true)
                    val newList =
                        _answers.value?.replace(newValue = highlightedAnswer) { it.id == highlightedAnswer.id }
                    newList?.let {
                        _answers.value = it
                        timer.cancel()
                        Handler(Looper.getMainLooper()).postDelayed({
                            handleRightToLeftAnimation()
                        }, ITEM_TAP_DELAY)
                    }
                }
            }
        })
    }

    init {
        addView(binding.root)
        with(binding) {
            rvQuestions.adapter = adapter

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

        answers.observeForever {
            adapter.submitList(it)
        }
    }

    fun setData(answers: List<AnswerUiModel>, question: QuestionUiModel, author: AuthorUiModel) {
        _author.value = author
        _question.value = question
        _answers.value = answers

        handleLeftToRightAnimation()
    }

    fun setTime(timeToShow: Long) {
        this.timeToShow = (timeToShow * 1000) + 1000
    }

    private fun setProgressBar() {
        binding.buffQuestion.questionTimeProgress.max = (timeToShow / TIMER_INTERVAL).toInt()
        with(binding.buffQuestion.questionTimeProgress) {
            this.max = (timeToShow / TIMER_INTERVAL).toInt()
            this.progress = 0
        }
        timer = object : CountDownTimer(timeToShow, TIMER_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val progress =
                    (timeToShow / TIMER_INTERVAL) - (millisUntilFinished / TIMER_INTERVAL)
                with(binding.buffQuestion) {
                    val seconds: Long = millisUntilFinished / TIMER_INTERVAL
                    questionTime.text = (seconds).toString()
                    binding.buffQuestion.questionTimeProgress.progress = (progress.toInt())
                }
            }

            override fun onFinish() {
                timeToShow = 0
                question_time.text = 0.toString()
                this.cancel()
                handleRightToLeftAnimation()
            }
        }.start()
    }

    private fun handleLeftToRightAnimation() {
        isLeftToRightAnimation = true
        isRecyclerViewTouchEnabled = true
        with(binding) {
            container.animate().setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    if (isLeftToRightAnimation) {
                        setProgressBar()
                    }
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }

            }).translationX(0f)
                .setInterpolator(AccelerateInterpolator()).duration = ANIM_DURATION
        }
    }

    private fun handleRightToLeftAnimation() {
        isLeftToRightAnimation = false
        binding.container.animate().translationX(-binding.container.width.toFloat())
            .setInterpolator(AccelerateInterpolator()).duration = ANIM_DURATION
    }
}