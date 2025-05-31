-- Add new UUID column
ALTER TABLE debt
ADD COLUMN debt_id CHAR(36);

-- Fill with UUIDs for existing rows
UPDATE debt
SET debt_id = UUID();

-- Add NOT NULL + PRIMARY KEY constraint
ALTER TABLE debt
MODIFY COLUMN debt_id CHAR(36) NOT NULL;

-- Set as primary key (if table didn't have one)
ALTER TABLE debt
ADD PRIMARY KEY (debt_id);

-- Optional: rename old id column for clarity
ALTER TABLE debt
CHANGE COLUMN id user_id CHAR(36) NOT NULL;
