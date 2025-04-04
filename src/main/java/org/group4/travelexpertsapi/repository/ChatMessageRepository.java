package org.group4.travelexpertsapi.repository;


import org.group4.travelexpertsapi.entity.ChatMessage;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT m FROM ChatMessage m WHERE " +
            "(m.senderId = :user1 AND m.recipientId = :user2) OR " +
            "(m.senderId = :user2 AND m.recipientId = :user1) " +
            "ORDER BY m.timestamp ASC")
    List<ChatMessage> findChatHistory(String user1, String user2);

    @Query(value = """
    WITH normalized AS (
        SELECT *, 
               CASE 
                   WHEN sender_id = :userId THEN recipient_id 
                   ELSE sender_id 
               END AS other_user
        FROM chat_messages
        WHERE sender_id = :userId OR recipient_id = :userId
    )
    SELECT DISTINCT ON (other_user) *
    FROM normalized
    ORDER BY other_user, timestamp DESC
""", nativeQuery = true)
    List<ChatMessage> findLatestInteractions(@Param("userId") String userId);


}

