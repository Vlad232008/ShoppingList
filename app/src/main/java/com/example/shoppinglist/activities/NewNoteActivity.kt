package com.example.shoppinglist.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.preference.PreferenceManager
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityNewNoteBinding
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.fragment.NoteFragment
import com.example.shoppinglist.utils.HtmlManager
import com.example.shoppinglist.utils.MyTouchListener
import com.example.shoppinglist.utils.TimeManager.getCurrentTime
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null
    private lateinit var defPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getSelectedTheme())
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        //binding.constLayout.background = getBackground()
        setContentView(binding.root)
        actionBarSetting()
        getNote()
        init()
        setTextSize()
        onClickColorPicker()
        onClickForceMenu()
        actionMenuCallback()
    }

    private fun getSelectedTheme():Int{
        return if(defPref.getString("theme_key", "red") == "red"){
            R.style.Theme_NewNoteLightRed
        } else {
            R.style.Theme_NewNoteLightBlue
        }
    }

    /*private fun getBackground():Drawable{
        return if(defPref.getString("theme_key", "red") == "red"){
            R.drawable.ic_gradient_red_burgundy.toDrawable()
        } else {
            R.drawable.ic_gradient_cyan_blue.toDrawable()
        }
    }*/

    private fun onClickForceMenu() = with(binding){
        binding.ibColor.setOnClickListener {
            if (binding.colorPicker.visibility == View.VISIBLE) {
                closeColorPicker()
            } else openColorPicker()
        }
        binding.ibItalic.setOnClickListener {
            setItalicForceSelectedText()
        }
        binding.ibBold.setOnClickListener {
            setBoldForceSelectedText()
        }
    }
    private fun onClickColorPicker() = with(binding){
        ibBlack.setOnClickListener{
            setColorForceSelectedText(R.color.picker_black)
        }
        ibRed.setOnClickListener{
            setColorForceSelectedText(R.color.picker_red)
        }
        ibOrange.setOnClickListener{
            setColorForceSelectedText(R.color.picker_orange)
        }
        ibYellow.setOnClickListener{
            setColorForceSelectedText(R.color.picker_yellow)
        }
        ibGreen.setOnClickListener{
            setColorForceSelectedText(R.color.picker_green)
        }
        ibBlue.setOnClickListener{
            setColorForceSelectedText(R.color.picker_blue)
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun init(){
        binding.colorPicker.setOnTouchListener(MyTouchListener())
    }

    private fun getNote() {
        val sNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        if (sNote != null) {
            note = sNote as NoteItem
            fillNote()
        }
    }

    private fun fillNote() = with(binding) {
        idTitle.setText(note?.title)
        idDescription.setText(HtmlManager.getFromHtml(note?.content!!).trim())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_save -> {
                if (binding.idTitle.text.isEmpty()){
                    val text = "Тема пуста!"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                }
                else setMainResult()
            }
            android.R.id.home -> {
                finish()
            }
            R.id.id_control_panel -> {
                if (binding.actionMenu.visibility == View.VISIBLE) {
                    closeActionMenu()
                } else openActionMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBoldForceSelectedText() = with(binding) {
        val startPos = idDescription.selectionStart
        val endPos = idDescription.selectionEnd

        val styles = idDescription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null
        if (styles.isNotEmpty()) {
            idDescription.text.removeSpan(styles[0])
        } else {
            boldStyle = StyleSpan(Typeface.BOLD)
            idDescription.text.setSpan(
                boldStyle,
                startPos,
                endPos,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            idDescription.text.trim()
            idDescription.setSelection(startPos)
        }
    }
    private fun setColorForceSelectedText(colorId: Int) = with(binding) {
        val startPos = idDescription.selectionStart
        val endPos = idDescription.selectionEnd
        val styles = idDescription.text.getSpans(startPos, endPos, ForegroundColorSpan::class.java)
        if (styles.isNotEmpty()) idDescription.text.removeSpan(styles[0])
            idDescription.text.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this@NewNoteActivity, colorId)),
                startPos,
                endPos,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            idDescription.text.trim()
            idDescription.setSelection(startPos)
    }
    private fun setItalicForceSelectedText() = with(binding) {
        val startPos = idDescription.selectionStart
        val endPos = idDescription.selectionEnd

        val styles = idDescription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var italicStyle: StyleSpan? = null
        if (styles.isNotEmpty()) {
            idDescription.text.removeSpan(styles[0])
        } else {
            italicStyle = StyleSpan(Typeface.ITALIC)
            idDescription.text.setSpan(
                italicStyle,
                startPos,
                endPos,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            idDescription.text.trim()
            idDescription.setSelection(startPos)
        }
    }

    private fun updateNote(): NoteItem? = with(binding) {
        note?.copy(
            title = idTitle.text.toString(),
            content = HtmlManager.toHtml(idDescription.text).trim()
        )
    }

    private fun actionBarSetting() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setMainResult() {
        var editState = "new"
        val tempNote: NoteItem? = if (note == null) {
            createNewNote()
        } else {
            editState = "update"
            updateNote()
        }
        val i = Intent().apply {
            putExtra(NoteFragment.NEW_NOTE_KEY, tempNote)
            putExtra(NoteFragment.EDIT_STATE_KEY, editState)
        }
        setResult(RESULT_OK, i)
        finish()
    }

    //Создание новой заметки
    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.idTitle.text.toString(),
            HtmlManager.toHtml(binding.idDescription.text).trim(),
            getCurrentTime(),
            ""
        )
    }

    //Открытие панели цветов
    private fun openActionMenu() {
        binding.actionMenu.visibility = View.VISIBLE
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
        binding.actionMenu.startAnimation(openAnim)
    }

    private fun closeActionMenu() {
        val closeAnim = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
        closeAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.actionMenu.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
        binding.actionMenu.startAnimation(closeAnim)
    }

    //Открытие панели цветов
    private fun openColorPicker() {
        binding.colorPicker.visibility = View.VISIBLE
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
        binding.colorPicker.startAnimation(openAnim)
    }

    //Закрытие панели цветов
    private fun closeColorPicker() {
        val closeAnim = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
        closeAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.colorPicker.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        binding.colorPicker.startAnimation(closeAnim)
    }
    private fun actionMenuCallback(){
        val actionMenuCallback = object: ActionMode.Callback{
            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                p1?.clear()
                return true
            }

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                p1?.clear()
                return true
            }

            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
                return true
            }

            override fun onDestroyActionMode(p0: ActionMode?) {

            }
        }
        binding.idDescription.customSelectionActionModeCallback = actionMenuCallback
    }

    private fun setTextSize() = with(binding){
        idTitle.setTextSize(defPref?.getString("title_size_key", "16"))
        idDescription.setTextSize(defPref?.getString("content_size_key", "14"))
    }
    private fun EditText.setTextSize(size :String?){
        if(size != null) this.textSize = size.toFloat()
    }

}