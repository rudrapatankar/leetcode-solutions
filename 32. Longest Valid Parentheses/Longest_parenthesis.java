import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;
class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}
class Solution {
    public int longestValidParentheses(String s) {
        int boundary = -1;
        int count=0;
        int maxCount=0;
        Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
        for (int position = 0; position < s.length(); ++position) {
            char next = s.charAt(position);
            if (next == '(' || next == '[' || next == '{') {
                opening_brackets_stack.push(new Bracket(next, position));
            } else if (next == ')' || next == ']' || next == '}') {
                if (opening_brackets_stack.empty()) {
                    boundary=position;
                    continue;
                } else {
                    Bracket ob = opening_brackets_stack.pop();
                    if (ob.Match(next)) {
                        if(opening_brackets_stack.empty())
                        count=position-boundary;
                        else
                        count = position-opening_brackets_stack.peek().position;
                    } 
                }
            }
            maxCount=Math.max(count,maxCount);
        }
        return maxCount;
    }
}