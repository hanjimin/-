package com.example.sec.woodongsa;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TopRatedFragment extends Fragment {
    public TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_top_rated_fragment, container, false);
        text = (TextView) rootView.findViewById(R.id.fuck);
        String wow = "우동사는\n" +
                "보험에 가입하실때 고객님이 거주하시는 지역에서 활동하시는\n" +
                "우수인증설계사를 고객님과 연결해 드리는 서비스입니다.\n" +
                "\n" +
                "우수인증설계사는 보험협회에서 인증하는 상위 10%의 보험설계사입니다.\n" +
                "우동사와 함께 프리미엄 보험서비스를 경험해 보세요.\n" +
                "\n" +
                "우동사의 모든 서비스를 홈페이지에서도 만나보실 수 있습니다.\n" +
                "홈페이지 : www.woodongsa.com\n" +
                "1. 가입하고자 하는 보험상품을 선택\n" +
                "2. 상담을 원하는 우수인증설계사를 선택, [연락처 남기기] 클릭!\n" +
                "3. 우수인증설계사와 상담 및 계약";
        text.setText(wow);
        return rootView;
    }
}
