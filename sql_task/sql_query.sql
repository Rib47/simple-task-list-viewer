SELECT employee_id, username, department, loc.location, job_title
FROM location loc
	LEFT JOIN (
		SELECT employee_id, username, null as department, location, null as job_title
		FROM user_internal
		UNION
		SELECT employee_id, username, department, location, job_title
		FROM user_external
	) users ON loc.location_id = users.location
WHERE loc.facility_manager = 'Location Manager Alpha';