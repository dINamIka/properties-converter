#!/usr/bin/env bash

if [ $# -lt 2 ]; then
	echo "Please specify a file!"
	exit 1
fi

script_path=$( cd "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )

abs_file_path=$2
if [[ $abs_file_path != /* ]]; then
	abs_file_path=$(pwd)/$abs_file_path
fi

exec $script_path/../properties-converter $1 $abs_file_path
