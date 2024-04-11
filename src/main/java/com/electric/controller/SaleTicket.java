package com.electric.controller;

/**
 * 
 * 
 * @author Administrator
 * @date 2020-9-17
 *
 */

public class SaleTicket {

    public static void main(String[] args) {
        // 创建一个初始化线程,若干个买票线程
        // 要等初始化线程初始化好了之后,才能买票

        // 创建一个Ticket类的对象,分别传给不同的线程
        Ticket ticket = new Ticket();

        Thread t1 = new InitTicketThread("初始化线程", ticket);

        // 等初始化线程初始化好车票之后,才能开始卖票
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 创建四个买票线程,开始卖票
        Thread t2 = new SaleTicketThread("窗口1", ticket);
        Thread t3 = new SaleTicketThread("窗口2", ticket);
        Thread t4 = new SaleTicketThread("窗口3", ticket);
        Thread t5 = new SaleTicketThread("窗口4", ticket);

    }
}

/**
 * 创建车票类、初始化车票
 * @author haokui
 *
 */
class Ticket {
    // 初始化200张票的空间
    private String[] tickets = new String[200];

    private int      index   = tickets.length - 1; // 指向数组的索引(第一张票)

    public Ticket() {

    }

    public void initTicket() {
        // 初始化车票,给数组的元素赋值,考虑是否要同步
        for (int i = 0; i < tickets.length; i++) {
            tickets[i] = "第" + (i + 1) + "号车票";
        }
    }

    public synchronized String saleTicket() throws NoTicketException {
        // 判断是否有票,有票的情况下再卖票,没有票呢,抛出异常,
        // 考虑是否需要同步

        if (index >= 0) {
            String s = tickets[index];

            // 故意制造了一个问题,出现多个线程共卖一张车票
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            tickets[index] = null;
            index--;
            return s;
        } else {
            throw new NoTicketException("没有车票了");
        }

    }
}

/**
 * 创建卖票线程类
 * @author haokui
 *
 */
class SaleTicketThread extends Thread {

    private Ticket ticket;

    public SaleTicketThread(String name, Ticket ticket) {
        super(name);
        this.ticket = ticket;
        this.start();
    }

    // 在run方法中卖车票
    public void run() {
        for (int i = 0; i < 60; i++) {
            try {
                String s = ticket.saleTicket();
                System.out.println(this.getName() + "卖票成功========>" + s);
            } catch (NoTicketException e) {
                System.out.println(this.getName() + " 卖票时发生异常!");
                e.printStackTrace();

                // 如果发生异常,说明没有车票了,就中断循环,不要在卖票了
                break;
            }
        }
    }
}

/**
 * 初始化车票的线程,负责初始化车票,也就是初始化Ticket类中的数组
 * @author haokui
 *
 */
class InitTicketThread extends Thread {
    private Ticket ticket;

    public InitTicketThread(String name, Ticket ticket) {
        super(name);
        this.ticket = ticket;
        this.start();
    }

    // 在run方法中初始化车票
    public void run() {
        ticket.initTicket();
    }
}

/**
 * 自定义异常
 * @author haokui
 *
 */
class NoTicketException extends Exception {
    public NoTicketException() {

    }

    public NoTicketException(String msg) {
        super(msg);
    }
}
