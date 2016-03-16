package com.example.sec.woodongsa;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class MoviesFragment extends Fragment {

    View rootView;
    ExpandableListView lv;
    private String[] groups;
    private String[][] children;


    public MoviesFragment() {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groups = new String[]{"1.고객센터의 운영시간은 어떻게 되나요?",
                "2.우수인증설계사입니다. 설계사 회원으로 가입하고 싶습니다.",
                "3.'연락처 남기기'를 했던 설계사한테 연락이 오지 않아요.",
                "4.'연락처 남기기'가 해당 설계사에게 전달되었는지 확인할 수 있나요?",
                "5.우수인증설계사는 일반설계사와 무엇이 다른가요?",
                "6.우수인증설계사인지 어떻게 확인할 수 있죠?",
                "7.계약을 할지 말지 결정을 내리기 어려울 경우 상담만 받아도 되나요?",
                "8.회원탈퇴를 하고 싶습니다."};

        children = new String[][]{
                {"평일 : 오전 10시 ~ 오후 6시\n점심 : 오후 12시 ~ 1시\n토,일,공휴일 : 휴무"},
                {"[홈 > 고객센터 > 문의하기]에 글을 남겨주시면 24시간(업무일기준) 안에 연락을 드립니다."},
                {"[홈 > 고객센터 > 문의하기]에 회원님의 아이디, 설계사의 이름 및 소속회사를 남겨주시면 바로 처리해 드리겠습니다."},
                {"'연락처 남기기'를 하시면 접수 되었다는 내용의 문자메세지를 받으시게 됩니다.\n" +
                        "혹시라도 문자메세지를 받지 못하셨을 경우 고객센터에 문의주시면 바로 처리해 드립니다."},
                {"<우수인증설계사 자격조건>\n" +
                        "\n" +
                        "손해보험협회\n" +
                        "-동일회사 3년 이상 근무 \n" +
                        "-6천만원 이상의 연소득\t\n" +
                        "-13회차 85%이상의 보험유지율\t\n" +
                        "-3%미만의 불완전판매율\n" +
                        "\n" +
                        "생명보험협회\n" +
                        "-동일회사 3년 이상 근무\n" +
                        "-전체설계사 평균이상의 연소득\n" +
                        "-13회차 90%이상, 25회차 80% 이상의 보험유지율\n" +
                        "-불완전판매 1건도 없음"},
                {"생명보험협회, 손해보험협회 홈페이지에서 조회하실 수 있습니다.\n" +
                        "생명보험 - http://cic.insure.or.kr/prime/primeSrchForm.klia\n" +
                        "손해보험 - http://isi.knia.or.kr/task04/work02.knia"},
                {"상담을 받으신다고 해서 계약을 해야 할 의무는 없으며 원하시는 만큼 다양한 설계사와 상담을 받으실 수 있습니다."},
                {"마이페이지 - 회원정보수정 - 회원탈퇴하기를 클릭하시면 탈퇴하실 수 있습니다."}
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lineup, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(groups, children));
        lv.setGroupIndicator(null);

    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private String[] groups;
        private String[][] children;

        public ExpandableListAdapter(String[] groups, String[][] children) {
            this.groups = groups;
            this.children = children;
            inf = LayoutInflater.from(getActivity());
        }

        @Override
        public int getGroupCount() {
            return groups.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = inf.inflate(R.layout.movies_list_item, parent, false);
                holder = new ViewHolder();

                holder.text = (TextView) convertView.findViewById(R.id.lblListItem);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getChild(groupPosition, childPosition).toString());

            return convertView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = inf.inflate(R.layout.list_group, parent, false);

                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.lblListHeader);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ImageView iv = (ImageView)  convertView.findViewById(R.id.photo);

            if (isExpanded) {
                iv.setImageResource(R.drawable.openicon2);
            } else {
                iv.setImageResource(R.drawable.openicon);
            }

            holder.text.setText(getGroup(groupPosition).toString());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private class ViewHolder {
            TextView text;
        }
    }
}