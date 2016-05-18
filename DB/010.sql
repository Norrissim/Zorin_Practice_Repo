SELECT u.name, m.text, m.date FROM messages AS m 
	LEFT JOIN users AS u ON m.user_id = u.user_id ORDER BY date DESC LIMIT 5;