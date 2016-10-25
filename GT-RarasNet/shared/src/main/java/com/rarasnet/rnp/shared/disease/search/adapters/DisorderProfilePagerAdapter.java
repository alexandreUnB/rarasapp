package com.rarasnet.rnp.shared.disease.search.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rarasnet.rnp.shared.disease.profile.associates.AssociatesFragment;
import com.rarasnet.rnp.shared.disease.profile.centers.AssociatedCentersFragment;
import com.rarasnet.rnp.shared.disease.profile.description.DescriptionFragment;
import com.rarasnet.rnp.shared.disease.profile.signs.DisorderSignsFragment;
import com.rarasnet.rnp.shared.disease.profile.statistics.StatisticsFragment;


/**
 * Created by Farina on 14/10/2015.
 */
public class DisorderProfilePagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private Context context;

    public DisorderProfilePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        //return PageFragment.newInstance(position + 1);

        switch (position) {
            case 0:
                return DescriptionFragment.newInstance(position);
            case 1:
                return DisorderSignsFragment.newInstance(position);
//            case 2:
//                return AssociatedCentersFragment.newInstance(position);
//            case 3:
//                return AssociatesFragment.newInstance(position);
            case 2:
                return StatisticsFragment.newInstance(position);
            default:
                return null;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        // return tabTitles[position];

        // getDrawable(int i) is deprecated, use getDrawable(int i, Theme theme) for min SDK >=21
        // or ContextCompat.getDrawable(Context context, int id) if you want support for older versions.
        // Drawable image = context.getResources().getDrawable(iconIds[position], context.getTheme());
        // Drawable image = context.getResources().getDrawable(imageResId[position]);

        return "";
    }
}