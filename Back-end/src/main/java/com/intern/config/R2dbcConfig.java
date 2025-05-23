package com.intern.config;

import com.intern.primary.enums.*;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * R2DBC Configuration for reactive database access.
 * This replaces the traditional JPA configuration to enable reactive database operations.
 */
@Configuration
@EnableR2dbcRepositories(basePackages = "com.intern.repository")
@EnableR2dbcAuditing
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    private final ConnectionFactory connectionFactory;

    public R2dbcConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public ConnectionFactory connectionFactory() {
        return connectionFactory;
    }
    
    /**
     * Configures custom type conversions for R2DBC.
     * 
     * @return R2dbcCustomConversions with custom converters
     */
    @Override
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        // Add Date <-> Instant converters
        converters.add(new DateToLongConverter());
        converters.add(new LongToDateConverter());
        
        // Add enum converters
        converters.add(new StringToVehicleStatusConverter());
        converters.add(new VehicleStatusToStringConverter());
        
        converters.add(new StringToVehicleLogTypeConverter());
        converters.add(new VehicleLogTypeToStringConverter());
        
        converters.add(new StringToReservationStatusConverter());
        converters.add(new ReservationStatusToStringConverter());
        
        converters.add(new StringToPaymentStatusConverter());
        converters.add(new PaymentStatusToStringConverter());
        
        converters.add(new StringToAccountStatusConverter());
        converters.add(new AccountStatusToStringConverter());
        
        converters.add(new StringToCarTypeConverter());
        converters.add(new CarTypeToStringConverter());
        
        converters.add(new StringToVanTypeConverter());
        converters.add(new VanTypeToStringConverter());
        
        return new R2dbcCustomConversions(getStoreConversions(), converters);
    }

    /**
     * Initializes the database with schema and data.
     * 
     * @return a ConnectionFactoryInitializer
     */
    @Bean
    public ConnectionFactoryInitializer initializer() {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        
        // Load schema and data SQL files
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema.sql"));
        // Uncomment to add initial data
        // populator.addScript(new ClassPathResource("data.sql"));
        
        initializer.setDatabasePopulator(populator);
        return initializer;
    }
    
    // Date Converters
    @WritingConverter
    public static class DateToLongConverter implements Converter<Date, Long> {
        @Override
        public Long convert(Date source) {
            return source != null ? source.getTime() : null;
        }
    }
    
    @ReadingConverter
    public static class LongToDateConverter implements Converter<Long, Date> {
        @Override
        public Date convert(Long source) {
            return source != null ? new Date(source) : null;
        }
    }
    
    // VehicleStatus Converters
    @ReadingConverter
    public static class StringToVehicleStatusConverter implements Converter<String, VehicleStatus> {
        @Override
        public VehicleStatus convert(String source) {
            return source != null ? VehicleStatus.valueOf(source) : null;
        }
    }
    
    @WritingConverter
    public static class VehicleStatusToStringConverter implements Converter<VehicleStatus, String> {
        @Override
        public String convert(VehicleStatus source) {
            return source != null ? source.toString() : null;
        }
    }
    
    // VehicleLogType Converters
    @ReadingConverter
    public static class StringToVehicleLogTypeConverter implements Converter<String, VehicleLogType> {
        @Override
        public VehicleLogType convert(String source) {
            return source != null ? VehicleLogType.valueOf(source) : null;
        }
    }
    
    @WritingConverter
    public static class VehicleLogTypeToStringConverter implements Converter<VehicleLogType, String> {
        @Override
        public String convert(VehicleLogType source) {
            return source != null ? source.toString() : null;
        }
    }
    
    // ReservationStatus Converters
    @ReadingConverter
    public static class StringToReservationStatusConverter implements Converter<String, ReservationStatus> {
        @Override
        public ReservationStatus convert(String source) {
            return source != null ? ReservationStatus.valueOf(source) : null;
        }
    }
    
    @WritingConverter
    public static class ReservationStatusToStringConverter implements Converter<ReservationStatus, String> {
        @Override
        public String convert(ReservationStatus source) {
            return source != null ? source.toString() : null;
        }
    }
    
    // PaymentStatus Converters
    @ReadingConverter
    public static class StringToPaymentStatusConverter implements Converter<String, PaymentStatus> {
        @Override
        public PaymentStatus convert(String source) {
            return source != null ? PaymentStatus.valueOf(source) : null;
        }
    }
    
    @WritingConverter
    public static class PaymentStatusToStringConverter implements Converter<PaymentStatus, String> {
        @Override
        public String convert(PaymentStatus source) {
            return source != null ? source.toString() : null;
        }
    }
    
    // AccountStatus Converters
    @ReadingConverter
    public static class StringToAccountStatusConverter implements Converter<String, AccountStatus> {
        @Override
        public AccountStatus convert(String source) {
            return source != null ? AccountStatus.valueOf(source) : null;
        }
    }
    
    @WritingConverter
    public static class AccountStatusToStringConverter implements Converter<AccountStatus, String> {
        @Override
        public String convert(AccountStatus source) {
            return source != null ? source.toString() : null;
        }
    }
    
    // CarType Converters
    @ReadingConverter
    public static class StringToCarTypeConverter implements Converter<String, CarType> {
        @Override
        public CarType convert(String source) {
            return source != null ? CarType.valueOf(source) : null;
        }
    }
    
    @WritingConverter
    public static class CarTypeToStringConverter implements Converter<CarType, String> {
        @Override
        public String convert(CarType source) {
            return source != null ? source.toString() : null;
        }
    }
    
    // VanType Converters
    @ReadingConverter
    public static class StringToVanTypeConverter implements Converter<String, VanType> {
        @Override
        public VanType convert(String source) {
            return source != null ? VanType.valueOf(source) : null;
        }
    }
    
    @WritingConverter
    public static class VanTypeToStringConverter implements Converter<VanType, String> {
        @Override
        public String convert(VanType source) {
            return source != null ? source.toString() : null;
        }
    }
}
