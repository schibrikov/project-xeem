package shook.xeem.list_adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import shook.xeem.BlankEditor;
import shook.xeem.R;
import shook.xeem.objects.BlankObject;
import shook.xeem.objects.QuestionObject;

public class BlankEditAdapter extends BaseAdapter{

    static final int EDIT_QUESTION_REQUEST = 29;

    Context context;
    LayoutInflater lInflater;
    BlankObject.Builder loadedBuilder;

    public BlankEditAdapter(Context _context) {
        this.context = _context;
        this.loadedBuilder = ((BlankEditor) context).getBuilder();
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return loadedBuilder.build().getQuestions().size();
    }

    @Override
    public Object getItem(int position) {
        return loadedBuilder.build().getQuestions().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        View view = convertView;

        if (view == null) {
            view = lInflater.inflate(R.layout.edit_question_list_item, parent, false);
        }

        final QuestionObject q = (QuestionObject) getItem(position);

        EditText questiontext = ((EditText) view.findViewById(R.id.questionText));
        questiontext.setText(q.getText());
        questiontext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                q.setText(editable.toString());
            }
        });

        // Click handler for REMOVE button
        Button removeBut = (Button) view.findViewById(R.id.removeButton);
        removeBut.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                loadedBuilder.rmQuestion(pos);
                notifyDataSetChanged();
            }
        });

        // Click handler for EDIT button
        Button editBut = (Button) view.findViewById(R.id.editButton);
        editBut.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                ((BlankEditor) context).startQuestionEdit(pos, q);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }
}
