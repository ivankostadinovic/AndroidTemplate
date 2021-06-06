package com.ivankostadinovic.template.screens;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
public class BaseFragment extends Fragment {
    public Toast toast;

    public void showMsg(String msg) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onDestroyView() {
        toast = null;
        super.onDestroyView();
    }


    public void observeDefaultEvents(BaseViewModel viewModel) {

    }
}
