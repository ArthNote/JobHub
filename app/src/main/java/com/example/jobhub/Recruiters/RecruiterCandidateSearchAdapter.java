package com.example.jobhub.Recruiters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobhub.Database;
import com.example.jobhub.R;
import com.example.jobhub.models.User;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecruiterCandidateSearchAdapter extends RecyclerView.Adapter<RecruiterCandidateSearchAdapter.MyViewHolder> implements Filterable {

    Context context;
    Activity activity;
    Cursor cursor;
    /*ArrayList<String> candidate_id, candidate_fname, candidate_lname, candidate_gender, candidate_phone,
            candidate_about, candidate_grad, candidate_post_grad, candidate_exp, candidate_specs, candidate_skills;*/

    private String recruiter_id;
    private List<User> userList = new ArrayList<>();
    private List<User> userListAll = new ArrayList<>();

    RecruiterCandidateSearchAdapter (Activity activity, Context context,/* ArrayList candidate_id, ArrayList candidate_fname, ArrayList candidate_lname,
                                     ArrayList candidate_gender, ArrayList candidate_phone, ArrayList candidate_about, ArrayList candidate_grad,
                                     ArrayList candidate_post_grad,ArrayList candidate_exp,ArrayList candidate_specs, ArrayList candidate_skills*/
                                     ArrayList<User> userList, String recruiter_id){
        this.context = context;
        this.activity = activity;
        this.recruiter_id = recruiter_id;
        /*
        this.candidate_id = candidate_id;
        this.candidate_fname = candidate_fname;
        this.candidate_lname = candidate_lname;
        this.candidate_gender = candidate_gender;
        this.candidate_phone = candidate_phone;
        this.candidate_about = candidate_about;
        this.candidate_grad = candidate_grad;
        this.candidate_post_grad = candidate_post_grad;
        this.candidate_exp = candidate_exp;
        this.candidate_skills = candidate_skills;
        this.candidate_specs = candidate_specs;
        */
        this.userList = userList;   //U
        this.userListAll = new ArrayList<>(userList);
    }
    @NonNull
    @Override
    public RecruiterCandidateSearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recruiter_candidates_recycleview_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecruiterCandidateSearchAdapter.MyViewHolder holder, int position) {
        /*
        holder.recruiter_candidate_row_exp.setText(String.valueOf(candidate_exp.get(position)));
        holder.recruiter_candidate_row_name.setText(String.valueOf(candidate_fname.get(position)));
        holder.recruiter_candidate_row_specs.setText(String.valueOf(candidate_specs.get(position)));*/

        User ulist = userList.get(position);
        holder.recruiter_candidate_row_exp.setText(String.valueOf(ulist.getExp()));
        String fname = String.valueOf(ulist.getFname());
        String lname = String.valueOf(ulist.getLname());
        String funame = fname + " " + lname;
        holder.recruiter_candidate_row_name.setText(funame);
        String gender = String.valueOf(ulist.getGender());
        if (gender.equals("Male")){
            holder.recruiter_search_image.setImageResource(R.drawable.employee);
        } else {
            holder.recruiter_search_image.setImageResource(R.drawable.women);
        }
        holder.recruiter_candidate_row_specs.setText(String.valueOf(ulist.getSpecs()));
        holder.recruiter_candidate_row_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, CandidateProfile.class);
                i.putExtra("user_id",String.valueOf(ulist.getid()));
                i.putExtra("recruiter_id", recruiter_id);
                i.putExtra("source", "search");
                activity.startActivityForResult(i,1);
            }
        });
    }



    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    //U
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constaint) {

            List<User> filteredList = new ArrayList<>();

            if (constaint.toString().isEmpty()) {
                filteredList.addAll(userListAll);
            } else {
                for (User user : userListAll) {
                    if (user.getFname().toLowerCase().contains(constaint.toString().toLowerCase())
                            || user.getLname().toLowerCase().contains(constaint.toString().toLowerCase())
                            || user.getSpecs().toLowerCase().contains(constaint.toString().toLowerCase())
                            || user.getSkills().toLowerCase().contains(constaint.toString().toLowerCase())
                            || String.valueOf(user.getExp()).toLowerCase().contains(constaint.toString().toLowerCase())) {
                        filteredList.add(user);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            userList.clear();
            userList.addAll((Collection<? extends User>) filterResults.values);
            notifyDataSetChanged();
        }
    };



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recruiter_candidate_row_exp, recruiter_candidate_row_name, recruiter_candidate_row_specs;
        LinearLayout recruiter_candidate_row_linearlayout;
        ShapeableImageView recruiter_search_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recruiter_candidate_row_exp = itemView.findViewById(R.id.recruiter_candidate_row_exp);
            recruiter_candidate_row_name = itemView.findViewById(R.id.recruiter_candidate_row_name);
            recruiter_candidate_row_specs = itemView.findViewById(R.id.recruiter_candidate_row_specs);
            recruiter_candidate_row_linearlayout = itemView.findViewById(R.id.recruiter_candidate_row_linearlayout);
            recruiter_search_image = itemView.findViewById(R.id.recruiter_search_image);
        }
    }

}
