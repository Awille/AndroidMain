package tips.fragmentdemo;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DemoFragment1 extends Fragment {

    private MainActivity mActivity;//用于与Activity通信

    @Override
    public void onAttach(Context context) {
        //碎片和活动(Context)首次建立关联时调用
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //当碎片创建视图(加载布局)时调用
        //如果不填加第三个参数未false，在动态修改Fragment时会出现问题
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //当与碎片相关联的活动被创建完成时调用
        super.onActivityCreated(savedInstanceState);

        /**
         * Fragment与Activity通信：
         * 通过getActivity()方法获取与当前Fragment相关联的Activity的实例，
         * 然后通过该实例与Activity通信
         * */
        mActivity = (MainActivity) getActivity();

        /**
         * Fragment与Fragment通信：
         * 先获取到与Fragment关联的Activity，然后再通过Activity获取
         * 需要通信的Fragment实例，实现Fragment与Fragment的通信
         * */
    }

    @Override
    public void onDestroyView() {
        //当onCreateView()方法中创建的视图被移除时调用
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        //当碎片和活动取消关联时调用
        super.onDetach();
    }
}
