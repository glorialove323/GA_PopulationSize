/**
 * 
 */
package com.gyy.MultiPops_GPS.Thread;

/**
 * @author Gloria
 *
 */
public class TestThread {

    public static void main(String[] args){
        TestThread threadtest = new TestThread();
        
        TestThread.firstThread firstThread = threadtest.new firstThread();
        firstThread.start();
        
        threadtest.new secondThread().start();
        
        threadtest.new thirdThread().start();
    }
    
    class firstThread extends Thread{
        
        public void run(){
            System.out.println("线程1--------");
            for(int i = 0;i<6;i++){
                System.out.println("线程1--------"+i);
            }
        }      
    }
    
    class secondThread extends Thread{
        
        public void run(){
            System.out.println("线程2--------");
            for(int i = 0;i<6;i++){
                System.out.println("线程2--------"+i);
            }
        }
    }
    
    class thirdThread extends Thread{
        
        public void run(){
            System.out.println("线程3--------");
            for(int i = 0;i<6;i++){
                System.out.println("线程3--------"+i);
            }
        }
    }
}
