package com.dream.highlighteditor.activity;


/*
 项目名：代码笔记
 包名：com.error.codenote
 文件名：EdiCodeFragment
 创建者：梦雪
 创建者QQ：2487686673
 创建时间：2018-11-09 12:07
 描述：编辑代码
 */
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.dream.highlighteditor.R;
import com.dream.highlighteditor.editor.TextEditor;
import com.dream.highlighteditor.view.SymbolView;

public class EditCodeActivity extends AppCompatActivity
 {
    private TextEditor content_edit;//内容编辑框
    private Toolbar toolbar;
    private SymbolView sv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editcode);
        content_edit = findViewById(R.id.textEditor);
        toolbar = findViewById(R.id.fragment_editor_Toolbar);
        //初始化Toolbar
        initToolbar();
        //初始化编辑器
        initEditor();
	}

    private void initToolbar() {
        // TODO: Implement this method
        toolbar.setTitle(R.string.edit_code);
        setSupportActionBar(toolbar);
    }

    private void initEditor() {
        // TODO: Implement this method
        //把焦点放到editor
        content_edit.requestFocus();
        View rootView = getWindow().getDecorView();
        sv = new SymbolView(this, rootView);
        sv.setVisible(true);
        sv.setOnSymbolViewClick(new SymbolView.OnSymbolViewClick() {
				@Override
				public void onClick(View view, String text) {
					if (text.equals("→")) {
						content_edit.paste("\t");
					} else {
						content_edit.paste(text);
					}
				}
			});
    }

    //菜单事件监听
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // TODO: Implement this method
        //获取编辑框内容
        String content = content_edit.getText().toString();
        switch (item.getItemId()) {
				//撤销
            case R.id.undo:
                content_edit.undo();
                break;
				//重做
            case R.id.redo:
                content_edit.redo();
                break;
				//复制代码片段
            case R.id.copycode:
                if (content.isEmpty()) {
					Toast.makeText(this, "代码为空，无需复制", Toast.LENGTH_SHORT).show();
                } else {
                    copyData(content);//复制代码
					Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //复制到剪切板
    private void copyData(String str) {
        //获取剪切板管理器
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //创建普通字符型ClipData
        ClipData cd = ClipData.newPlainText("title", str);
        //将ClipData内容复制到剪切板
        cm.setPrimaryClip(cd);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO: Implement this method
		getMenuInflater().inflate(R.menu.menu_editcode_action, menu);
		return true;
	}
}
