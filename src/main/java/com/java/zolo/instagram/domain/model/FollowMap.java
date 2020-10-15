package com.java.zolo.instagram.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "follow_map", uniqueConstraints=
@UniqueConstraint(columnNames={"fk_target_user", "fk_follower"}, name = "oneFollowPerUser"))
@Accessors(chain = true)
public class FollowMap {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_target_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User targetUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_follower", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User follower;

    @CreationTimestamp
    @Column(name = "followed_at")
    private Timestamp followedAt;
}
