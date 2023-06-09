package org.eclipse.javatest;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.eclipse.mosaic.protos.federate.FederateMessageWrapper;
import org.eclipse.mosaic.protos.federate.SimulationStep;
import org.eclipse.mosaic.protos.federate.MediatorStatus;
import org.eclipse.mosaic.protos.common.StatusType;


public class JavaTest {
    ServerSocket serverSocket;
    Socket clientSocket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    boolean connected = true;

    public static void main(String[] args) {
        try {
            new JavaTest().execute(args);
            System.exit(0);
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    public void execute(String[] arguments) throws Exception {
        if (arguments.length == 0) {
            System.out.println("arguments length is zero");
        }
        serverSocket = new ServerSocket(2017);
        System.out.println("ServerSocket is created");
        clientSocket = serverSocket.accept();
        System.out.println("Socket is accepted");

        dataInputStream = new DataInputStream(clientSocket.getInputStream());
        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

        FederateMessageWrapper messageWarpper;

        while (true) {
            int length = dataInputStream.readInt();
            if (length == 0) {
                break;
            }
            byte[] buffer = new byte[length];
            dataInputStream.readFully(buffer);

            messageWarpper = FederateMessageWrapper.parseFrom(buffer);

            switch (messageWarpper.getMessageCase()) {
                // SimulationStep
                case SIMULATIONSTEP:
                    System.out.println("simulation step");
                    simulationStepProcess(messageWarpper.getSimulationStep());
                // MediatorStatus
                case MEDIATORSTATUS:
                    System.out.println("mediator status");
                    mediatorStatusProcess(messageWarpper.getMediatorStatus());
            }

            if (!connected) {
                break;
            }
        }
        dataInputStream.close();
        dataOutputStream.close();
        clientSocket.close();
        serverSocket.close();
    }

    private void simulationStepProcess(SimulationStep simulationStep) {
        System.out.println(simulationStep.getFedName());
    }


    private void mediatorStatusProcess(MediatorStatus mediatorStatus) {
        if (mediatorStatus.getStatus() == StatusType.RUNNING) {
            System.out.println("Mediator is runnning");
        }

        if (mediatorStatus.getStatus() == StatusType.CLOSING) {
            connected = false;
        }
    }
}
