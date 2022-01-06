package at.htl.controller;

import at.htl.EmployeeReply;
import at.htl.EmployeeRequest;
import at.htl.EmployeeSeeker;
import at.htl.controller.EmployeeRepository;
import at.htl.entity.Employee;
import io.quarkus.grpc.GrpcService;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;

@GrpcService
public class EmployeeService implements EmployeeSeeker {

    @Inject
    EmployeeRepository repo;

    @Override
    @Blocking
    public Uni<EmployeeReply> getEmp(EmployeeRequest request) {
        Employee emp = repo
                .findById((long) request.getId());

        return Uni.createFrom().item(() -> {
                    return EmployeeReply.newBuilder()
                            .setFirstName(emp.firstName)
                            .setLastName(emp.lastName)
                            .setSalary(emp.salary)
                            .build();
                }
        );
    }
}
