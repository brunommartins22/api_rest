/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.erplibrary;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 *
 * @author Bruno Martins
 */
public class ProcessInstance implements Runnable {

    private Process process;
    private PrintStream processInput;
    private BufferedReader processOutput;
    private String processName;

    public ProcessInstance(String processName, Process process) {

        this.setProcessName(processName);
        this.setProcess(process);

    }

    public Process getProcess() {

        return process;

    }

    public void setProcess(Process process) {

        this.process = process;

        setStreams();

    }

    protected void setStreams() {

        this.processInput = new PrintStream(getProcess().getOutputStream());

        this.processOutput = new BufferedReader(new InputStreamReader(getProcess().getInputStream()));

    }

    public void input(String message) {
        processInput.println(message);
        processInput.flush();
    }

    public String getProcessName() {

        return processName;

    }

    public void setProcessName(String processName) {

        this.processName = processName;

    }

    public void run() {

        try {

            boolean primeiraVez = true;

            while (true) {

                String line = processOutput.readLine();

                if (line != null) {

                    line = line.trim();

                }

                if (line == null || (line.length() == 0 && !primeiraVez)) {

                    System.out.println("Fim");

                    break;

                }

                if (line.length() != 0) {

                    System.out.println(line);

                    primeiraVez = false;

                }

            }

        } catch (Exception e) {
            System.out.println("fim com erro");

            e.printStackTrace();
        }
    }

}
