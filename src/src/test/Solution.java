package test;

import java.util.ArrayList;
import java.util.List;



class Solution extends ArrayList<String> {
	List<Node> sNode;

    public void addSolution(String solution) {
        this.add(solution);
    }

    
    
    public Solution Reverse(){
        String temp;
        for(int i=0;i<this.size()/2;i++)
        {
            temp=this.get(i);
            this.set(i,this.get(this.size()-i-1));
            this.set(this.size()-i-1,temp);

        }
        return this;
    }

    
    
    @Override
    public String toString(){
        String str="";
        String str2="";
        for(int i=0;i<this.size();i++) {
                str2 = this.get(i);
                if(!(str2.charAt(4)=='0')) {
                    str += this.get(i);
                    str += "\n";
                }
        }
        str2="";
        for (int i=0;i<str.length()-1;i++)
            str2+=str.charAt(i);
        return str2;
    }





    public Solution toStrin(Solution s) {
        Solution toSend=new Solution();
        while (!(s.size()==1)) {
            String a = s.get(0).toString();
            String b = s.get(1).toString();
            if (a.charAt(0) == b.charAt(0)) {
                if (a.charAt(2) == (b.charAt(2) + 1))
                    toSend.addSolution("LEFT");
                else
                    toSend.addSolution("RIGHT");
            }
            if (a.charAt(2) == b.charAt(2)) {
                if (a.charAt(0) == (b.charAt(0) + 1))
                    toSend.addSolution("UP");
                else
                    toSend.addSolution("DOWN");
            }
            s.remove(0);
        }
        return toSend;
    }
}
