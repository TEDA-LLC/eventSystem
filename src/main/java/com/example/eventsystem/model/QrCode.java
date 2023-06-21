package com.example.eventsystem.model;//package com.example.tedasystem.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//import org.hibernate.annotations.GenericGenerator;
//import org.hibernate.annotations.Parameter;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@ToString
//@Entity
//@Builder
//public class QrCode {
//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(
//            name = "UUID",
//            strategy = "org.hibernate.id.UUIDGenerator",
//            parameters = {
//                    @Parameter(
//                            name = "uuid_gen_strategy_class",
//                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
//                    )
//            }
//    )
//    @Column(updatable = false, nullable = false)
//    private UUID id;
//
//    @ManyToOne
//    private User user;
//
//    @ManyToOne
//    private Product product;
//
//    private LocalDateTime registeredTime = LocalDateTime.now();
//    private LocalDateTime arrivalTime;
//    @ManyToOne
//    private Company company;
//
//    @ManyToOne
//    @JoinColumn(unique = true)
//    private Request request;
//}
