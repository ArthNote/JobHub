package com.example.jobhub.Candidates;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jobhub.Candidates.fragments.AcceptedFragment;
import com.example.jobhub.Candidates.fragments.PendingFragment;
import com.example.jobhub.Candidates.fragments.RejectedFragment;
import com.example.jobhub.Candidates.fragments.SavedFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    private String user_id;

    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String user_id) {
        super(fragmentActivity);
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PendingFragment(user_id);
            case 1:
                return new AcceptedFragment(user_id);
            case 2:
                return new RejectedFragment(user_id);
            case 3:
                return new SavedFragment(user_id);
            default:
                return new PendingFragment(user_id);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
