package com.example.demo.protocol.Utils;

import java.util.*;

/**
 * 能够计算带括号的四则运算的计算器
 */
public class Computer {
    public  int compute(String expression){
        return computeReversePoland(expression);
    }

    /**
     * 对表达式进行分隔，将运算符与数字分开
     * @param expression 表达式
     * @param operators 运算符集合
     * @return 分隔好的字符串列表
     */
    public   List<String> spiltExpression(String expression,String operators){
        expression=expression.replaceAll("\\s+", "");
        List<String> list=new ArrayList<>();
        //打印分隔符
        StringTokenizer strToke=new StringTokenizer(expression,operators,true);
        while(strToke.hasMoreTokens()){
            list.add(strToke.nextToken());
        }
        return list;
    }

    /**
     * 判断一个字符串是否是由数字组成。
     * @param number
     * @return
     */
    public boolean isNumber(String number){
        if(number.matches("[0-9]+")) {
            return true;
        }else {
            return false;
        }
    }
    public boolean isOperators(String operators){
        if(operators.matches("[\\+\\-\\*\\/\\(\\)\\$]"))
        {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取优先级
     * @param operator 操作符
     * @return
     */
    private int getPriorities(String operator){
        switch (operator){
            case "(": return 4;
            case "*": return 3;
            case "/": return 3;
            case "+": return 2;
            case "\\": return 2;
            case "-": return 2;
            case ")": return 1;
            default: return -1;
        }
    }

    /**
     * 比较优先级，operator1>operator2则返回true，否则为false
     * @param operator1
     * @param operator2
     * @return
     */
    private boolean ComparePriorities(String operator1,String operator2){
        int a=getPriorities(operator1);
        int b=getPriorities(operator2);
        if(a>b) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 中缀表达式转逆波兰表达式
     * @return
     */
    private List<String> toReversePolandExpression(String express){
        List<String> expression=spiltExpression(express,"+-*/()$");
        List<String> ReversePoland=new ArrayList<>();
        Stack<String> stack=new Stack<>();
        Iterator<String> iterator=expression.iterator();

        while (iterator.hasNext()) {
            String tmp=iterator.next();

            if (isNumber(tmp)) {
                ReversePoland.add(tmp);
            }else if(stack.empty()) {
                stack.push(tmp);
            }else if(stack.peek().equals("(")){
                stack.push(tmp);
            }else {
                String top = stack.peek();
                if ("(".equals(tmp)) {
                    stack.push(tmp);
                } else if (")".equals(tmp)) {
                    while (true) {
                        String s = stack.pop();
                        if (s.equals("(")) {
                            break;
                        } else {
                            ReversePoland.add(s);
                        }
                    }
                } else {
                    if (ComparePriorities(tmp, top)) {
                        stack.push(tmp);
                    } else {
                        ReversePoland.add(stack.pop());
                        stack.push(tmp);
                    }
                }
            }
        }
        while (!stack.empty()){
            ReversePoland.add(stack.pop());
        }
        return ReversePoland;
    }
    private int computeReversePoland(String exprssion){
        List<String> ReversePoland=toReversePolandExpression(exprssion);
        Stack<Integer> stack=new Stack<>();
        Iterator<String> iterator=ReversePoland.iterator();

        while (iterator.hasNext()){
            String tmp=iterator.next();
            if(isNumber(tmp)){
                Integer i=Integer.valueOf(tmp);
                stack.push(i);
            }else{
                Integer result=0;
                Integer num1=stack.pop();
                Integer num2=stack.pop();

                switch (tmp){
                    case "+":
                        result=num2+num1;
                        break;
                    case "-":
                        result=num2-num1;
                        break;
                    case "*":
                        result=num2*num1;
                        break;
                    case "/":
                        result=num2/num1;
                        break;
                    case "$":
                        if(num2%num1==0) {
                            result = num2 / num1;
                        }else {
                            result=num2/num1+1;
                        }
                        break;
                    default:
                        result= -1;
                }
                stack.push(result);
            }
        }
        return stack.pop();
    }

}
