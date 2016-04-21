package shook.xeem;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import shook.xeem.activities.BlankEditActivity;
import shook.xeem.list_adapters.AnswerAdapter;
import shook.xeem.objects.AnswerObject;
import shook.xeem.objects.QuestionObject;

public class QuestionEditFragment extends Fragment {


    QuestionObject editable;
    AnswerAdapter answerAdapter;
//    ArrayAdapter<AnswerObject> answerAdapter;

    QuestionEditFragment self = this;

    public QuestionEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_question_edit, null);

        final Context context = getActivity();

        // Get editable question from sent bundle
        editable = (new Gson()).fromJson(getArguments().getString("editable"), QuestionObject.class);

        final ListView answerList = (ListView) view.findViewById(R.id.answerList);
        answerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        answerAdapter = new AnswerAdapter(this.getContext(), editable);
        answerList.setAdapter(answerAdapter);

//        answerAdapter = new ArrayAdapter<AnswerObject>(context, android.R.layout.simple_list_item_single_choice, editable.getAnswers());
//        answerList.setAdapter(answerAdapter);

        view.findViewById(R.id.add_answer_button).setOnClickListener(addAnswer);
        view.findViewById(R.id.edit_question_finish).setOnClickListener(finishEdit);

        return view;

    }

    View.OnClickListener addAnswer = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Type the answer");
            final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editable.putAns(input.getText().toString());
                    answerAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    };

    View.OnClickListener finishEdit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((BlankEditActivity)getActivity()).receiveEditedQuestion(getArguments().getInt("index"), editable);
            getActivity().getSupportFragmentManager().popBackStack();
            getActivity().findViewById(R.id.question_edit_fragment_container).setVisibility(View.GONE);
        }
    };

}
