package com.grean.testtools.ui.notifications;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.grean.testtools.R;
import com.grean.testtools.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment implements View.OnClickListener {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private TextView tvSsid,tvIp,tvPort;
    private Switch swServer;
    private NotificationsModel model;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initView(root);
        model = new NotificationsModel(getActivity().getPreferences(Activity.MODE_PRIVATE));
        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private void initView(View v){
        tvSsid = v.findViewById(R.id.tvSsid);
        tvSsid.setOnClickListener(this);
        tvIp = v.findViewById(R.id.tvServerIp);
        tvIp.setOnClickListener(this);
        tvPort = v.findViewById(R.id.tvServerPort);
        tvPort.setOnClickListener(this);
        swServer = v.findViewById(R.id.swServer);
        swServer.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onClick(View v) {
        EditText et;
        switch (v.getId()){
            case R.id.tvSsid:
                et = new EditText(getActivity());
                new AlertDialog.Builder(getActivity()).setTitle("输入SSID").setView(et).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("取消",null).show();
                break;
            case R.id.tvServerIp:
                et = new EditText(getActivity());
                new AlertDialog.Builder(getActivity()).setTitle("输入IP").setView(et).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("取消",null).show();
                break;
            case R.id.tvServerPort:
                et = new EditText(getActivity());
                new AlertDialog.Builder(getActivity()).setTitle("输入端口").setView(et).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("取消",null).show();
                break;
            case R.id.swServer:

                break;
            default:

                break;

        }
    }
}