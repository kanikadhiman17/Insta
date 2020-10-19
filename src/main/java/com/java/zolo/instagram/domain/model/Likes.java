package com.java.zolo.instagram.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "likes", uniqueConstraints=
@UniqueConstraint(columnNames={"fk_user", "fk_target_id"}, name = "oneLikePerUser"))
@Accessors(chain = true)
public class Likes {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @AnyMetaDef(idType = "long", metaType = "string",
            metaValues = {
                    @MetaValue(targetEntity = Comments.class, value = "Comment"),
                    @MetaValue(targetEntity = Posts.class, value = "Post"),
            })
    @Any(metaColumn = @Column(name = "target_type"))
    @JoinColumn(name = "fk_target_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Object targetId;

    @CreationTimestamp
    @Column(name = "liked_at")
    private Timestamp likedAt;
}
