package top.harumill.calculator.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import top.harumill.calculator.Calculator.Calculator
import com.example.app1.R
import kotlinx.android.synthetic.main.activity_date.*
import java.time.LocalDate

class DateActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)

        Calculator.init()

        val curY = Calculator.curDate.year
        val curM = Calculator.curDate.monthValue
        val curD = Calculator.curDate.dayOfMonth

        btn_date_before.text = "从 ${curY}-${curM}-${curD} 开始"
        btn_date_after.text = " 距离 ${curY}-${curM}-${curD} 还有"

        btn_date_before.setOnClickListener { showDatePickerDialog(btn_date_before) }
        btn_date_after.setOnClickListener { showDatePickerDialog(btn_date_after) }

        edit_day_before.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val cnt = p0.toString()
                if (cnt.isNotEmpty()) {
                    val res = Calculator.getDayBefore(cnt.toLong())
                    res_date_before.text = "${res.year}-${res.monthValue}-${res.dayOfMonth}"
                } else {
                    res_date_before.text = "请输入数字计算"
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        edit_day_after.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val cnt = p0.toString()
                if (cnt.isNotEmpty()) {
                    val res = Calculator.getDayAfter(cnt.toLong())
                    res_date_after.text = "${res.year}-${res.monthValue}-${res.dayOfMonth}"
                } else {
                    res_date_after.text = "请输入数字计算"
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePickerDialog(btn: Button) {
        val setDate = when (btn) {
            btn_date_before -> Calculator.startDate
            else -> Calculator.endDate
        }
        val setY: Int = setDate.year
        val setM: Int = setDate.monthValue
        val setD: Int = setDate.dayOfMonth

        val dpd = DatePickerDialog(this, { _, nowY, nowM, nowD ->
            run {
                val setStr = "${nowY}-${nowM + 1}-${nowD}"
                when (btn) {
                    btn_date_after -> {
                        Calculator.endDate = LocalDate.of(nowY, nowM + 1, nowD)
                        btn_date_after.text = "距离 $setStr 还有"
                        textView8.text = Calculator.getDaysBetween().toString() + " 天"
                    }
                    else -> {
                        Calculator.startDate = LocalDate.of(nowY, nowM + 1, nowD)
                        btn_date_before.text = "从 $setStr 开始"
                        if (edit_day_before.text.isNotEmpty()) {
                            val cnt = edit_day_before.text.toString()
                            val res = Calculator.getDayBefore(cnt.toLong())
                            res_date_before.text = "${res.year}-${res.monthValue}-${res.dayOfMonth}"
                        }
                        if (edit_day_after.text.isNotEmpty()) {
                            val cnt = edit_day_after.text.toString()
                            val res = Calculator.getDayAfter(cnt.toLong())
                            res_date_after.text = "${res.year}-${res.monthValue}-${res.dayOfMonth}"
                        }
                        textView8.text = Calculator.getDaysBetween().toString() + " 天"
                    }
                }
            }
        }, setY, setM - 1, setD)

        dpd.show()
    }
}