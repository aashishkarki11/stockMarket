package hamro.stockmarket.stockmarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a stock entity in the database.
 * This entity is mapped to the "stock" table in the database and contains fields such as
 * the unique identifier (id), UUID, stock details, and API key.
 * Author: [Aashish Karki]
 */
@Entity
@Builder
@Table(name = "stock")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Generated(GenerationTime.ALWAYS)
  @Column(name = "uuid", columnDefinition = "uuid DEFAULT uuid_generate_v4()", insertable = false, updatable = false)
  private UUID uuid;

  @Column(columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private String stockDetails;

  @org.hibernate.annotations.Generated(GenerationTime.ALWAYS)
  @Column(name = "api_key", columnDefinition = "uuid DEFAULT uuid_generate_v4()", insertable = false, updatable = false)
  private UUID apiKey;

  private LocalDateTime createdAt;
}
