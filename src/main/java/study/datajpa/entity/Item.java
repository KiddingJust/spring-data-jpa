package study.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)  //@CreatedDate 쓰기 위함
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //기본생성자 필수. protected로.
public class Item implements Persistable<String> {

    @Id //@GeneratedValue
    private String id;

    @CreatedDate        //persist 전에 생성
    private LocalDateTime createdDate;

    public Item(String id) {
        this.id = id;
    }

    @Override
    public String getId(){
        return id;
    }

    @Override
    public boolean isNew(){
        return createdDate == null;
    }
}
