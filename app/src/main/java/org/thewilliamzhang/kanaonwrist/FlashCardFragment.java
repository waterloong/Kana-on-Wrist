/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

public class FlashCardFragment extends Fragment {

    private FlashCardFragment oppositeFragment;
    private boolean isFront;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.inner_fragment, container, false);
        String[] titles = getArguments().getStringArray("titles");

        LinearLayout titleLayout = (LinearLayout) rootView.findViewById(R.id.titles);
        String[] contents = getArguments().getStringArray(isFront ? "hiragana" : "katakana");
        LinearLayout contentLayout = (LinearLayout) rootView.findViewById(R.id.contents);
        for (int i = 0; i < titles.length; i++) {
            TextView titleView = (TextView) titleLayout.getChildAt(i);
            titleView.setText(titles[i]);
            TextView contentView = (TextView) contentLayout.getChildAt(i);
            contentView.setText(contents[i]);
        }
        return rootView;
    }

    public void setOppositeFragment(FlashCardFragment oppositeFragment) {
        this.oppositeFragment = oppositeFragment;
    }

}
