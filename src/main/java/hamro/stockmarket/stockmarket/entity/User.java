package hamro.stockmarket.stockmarket.entity;

import hamro.stockmarket.stockmarket.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

/**
 * Represents a user entity in the stock market system.
 * This entity is mapped to the "users" table in the database and contains fields such as
 * the unique identifier (id), UUID, email, user role, user details, and activation
 * status.
 * Author: [Aashish Karki]
 */
@Entity
@Builder
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Generated(GenerationTime.ALWAYS)
  @Column(name = "uuid", columnDefinition = "uuid DEFAULT uuid_generate_v4()", insertable = false, updatable = false)
  private UUID uuid;

  private String email;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private String userDetails;

  private String isActive;

  @ManyToOne
  @JoinColumn(name = "stock_id")
  private Stock stock;

}
