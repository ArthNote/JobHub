package com.example.jobhub.Recruiters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.jobhub.Recruiters.fragments.RAcceptedFragment;
import com.example.jobhub.Recruiters.fragments.RPendingFragment;
import com.example.jobhub.Recruiters.fragments.RRejectedFragment;

public class RecruiterViewPagerAdapter extends FragmentStateAdapter {
    private String user_id;
    public RecruiterViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String user_id) {
        super(fragmentActivity);
        this.user_id = user_id;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new RPendingFragment(user_id);
            case 1:
                return new RAcceptedFragment(user_id);
            case 2:
                return new RRejectedFragment(user_id);
            default:
                return new RPendingFragment(user_id);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
