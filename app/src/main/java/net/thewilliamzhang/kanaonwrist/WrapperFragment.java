package net.thewilliamzhang.kanaonwrist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by william on 2017-09-04.
 */

public class WrapperFragment extends Fragment implements View.OnClickListener {

    FlashCardFragment frontFragment = new FlashCardFragment();
    FlashCardFragment backFragment = new FlashCardFragment();

    boolean isFlipped = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wrapper_fragment, container, false);
        frontFragment.setArguments(getArguments());
        frontFragment.setFront(true);
        frontFragment.setOnClickListener(this);
        backFragment.setOnClickListener(this);
        backFragment.setArguments(getArguments());
        FragmentManager childFragmentManager = getChildFragmentManager();
        if (childFragmentManager.getBackStackEntryCount() == 0) {
            childFragmentManager.beginTransaction().add(R.id.container, frontFragment).commit();
        }
        return rootView;
    }

    private void flip() {
        isFlipped = !isFlipped;
        this.getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.container, isFlipped ? backFragment : frontFragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
        flip();
    }
}
