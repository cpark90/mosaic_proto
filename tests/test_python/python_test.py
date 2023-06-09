import socket
import struct

from federate_message_wrapper_pb2 import FederateMessageWrapper
from simulation_step_pb2 import SimulationStep
from mediator_status_pb2 import MediatorStatus
from types_pb2 import StatusType

class PythonTest:
    def __init__(self, fed_name):
        # Connect to the server
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket.connect(('localhost', 2017))
        print("connection succeed")
    
    
    def send_simulation_step(self, fed_name):
        simulation_step = SimulationStep()
        simulation_step.fedName = fed_name

        message_wrapper = FederateMessageWrapper()
        message_wrapper.simulationStep.CopyFrom(simulation_step)
        self.socket.sendall(struct.pack('>I', message_wrapper.ByteSize()))
        self.socket.sendall(message_wrapper.SerializeToString())


    def send_mediator_status(self, status):
        mediator_status = MediatorStatus()
        mediator_status.status = status

        message_wrapper = FederateMessageWrapper()
        message_wrapper.mediatorStatus.CopyFrom(mediator_status)
        self.socket.sendall(struct.pack('>I', message_wrapper.ByteSize()))
        self.socket.sendall(message_wrapper.SerializeToString())

if __name__ == "__main__":
    python_test = PythonTest("federate")
    
    python_test.send_simulation_step("federate")

    python_test.send_mediator_status(StatusType.RUNNING)
    
    python_test.send_mediator_status(StatusType.CLOSING)