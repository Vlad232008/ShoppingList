package com.example.shoppinglist.activities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityNewNoteBinding
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.fragment.NoteFragment
import com.example.shoppinglist.utils.HtmlManager
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSetting()
        getNote()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    private fun getNote(){
        val sNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        if (sNote != null) {
            note = sNote as NoteItem
            fillNote()
        }
    }

    private fun fillNote()= with(binding){
          idTitle.setText(note?.title)
          idDescription.setText(HtmlManager.getFromHtml(note?.content!!).trim())
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_save) {
                setMainResult()
        } else if (item.itemId == android.R.id.home) {
            finish()
        }
        else if (item.itemId == R.id.id_bold) {
            setBoldForceSelectedText()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBoldForceSelectedText() = with(binding) {
        val startPos = idDescription.selectionStart
        val endPos = idDescription.selectionEnd

        val styles = idDescription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null
        if (styles.isNotEmpty()){
            idDescription.text.removeSpan(styles[0])
        }
        else {
            boldStyle = StyleSpan(Typeface.BOLD)
        }
        idDescription.text.setSpan(boldStyle, startPos,endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        idDescription.text.trim()
        idDescription.setSelection(startPos)
    }
    private fun updateNote(): NoteItem? = with(binding){
        note?.copy(title = idTitle.text.toString(),
        content = HtmlManager.toHtml(idDescription.text).trim()
        )
    }

    private fun actionBarSetting() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setMainResult() {
        var editState = "new"
        val tempNote: NoteItem? = if (note == null){
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

    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.idTitle.text.toString(),
            HtmlManager.toHtml(binding.idDescription.text).trim(),
            getCurrentTime(),
            ""
        )
    }

    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm:ss - dd/MM/yy", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
}