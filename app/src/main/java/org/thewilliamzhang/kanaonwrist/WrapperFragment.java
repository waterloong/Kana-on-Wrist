package org.thewilliamzhang.kanaonwrist;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by william on 2017-09-04.
 */

public class WrapperFragment extends Fragment {

    FlashCardFragment frontFragment = new FlashCardFragment();
    FlashCardFragment backFragment = new FlashCardFragment();
    {
        frontFragment.setFront(true);
        frontFragment.setOppositeFragment(backFragment);
        backFragment.setOppositeFragment(frontFragment);
    }

    boolean isFlipped = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wrapper_fragment, container, false);
        frontFragment.setArguments(getArguments());
        backFragment.setArguments(getArguments());
        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction().add(R.id.container, frontFragment).commit();
        }
        View containerView = rootView.findViewById(R.id.container);
        containerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip();
            }
        });
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
}
