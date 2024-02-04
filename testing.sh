#!/bin/bash

# Check if a file path is provided as an argument
if [ $# -eq 0 ]; then
	echo "Usage: $0 <file_with_commands>"
	exit 1
fi

# Read the file line by line
while IFS= read -r line; do
	# Check if the line is empty
	if [ -z "$line" ]; then
		# Execute the accumulated multiline command
		if [ -n "$multiline_command" ]; then
			echo "Executing: $multiline_command"
			eval "$multiline_command"

			# Check the exit status of the command
			if [ $? -ne 0 ]; then
				echo "Error executing command: $multiline_command"
				exit 1
			fi

			# Reset the multiline command variable
			multiline_command=""
		fi
	else
		# Append the current line to the multiline command
		multiline_command+=" $line"
	fi
done <"$1"

echo "All commands executed successfully"
