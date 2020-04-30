#!/usr/bin/env bash

if (($# != 1)); then
    echo "Usage $0 <directory>"
    exit 1
fi

RED='\033[0;31m'
GREEN='\033[0;32m'
NOCOLOR='\033[0m'

tests=$(realpath "$1")
threshold=${2:-1}
PROGRAM="/home/jmolinski/dv/po/cover/out/artifacts/cover_jar/cover.jar"

total=0
correct=0
leaked=0

function traverse_folder() {
  folder="$1"
  shopt -s nullglob
  for f in "$folder"/*.in; do
    randfloat=$(printf '0%s\n' $(echo "scale=8; $RANDOM/32768" | bc ))
    if (( $(echo "$randfloat < $threshold" |bc -l) )); then
      run_test "$f"
    fi
  done

  shopt -s nullglob
  for d in "$folder"/*/; do
    echo "$d"
    traverse_folder "$(realpath "$d")"
  done
}

function run_test() {
  input_file="$1"
  output_file=${input_file//.in/.out}

  ((total++))
  echo -e "\e[1mTest $f \e[0m"

  cat "$input_file" | java -jar $PROGRAM 1>"$temp_out"

    if cmp -s "$output_file" "$temp_out" ; then
        echo -e "${GREEN}stdout ok${NOCOLOR}"
        ((correct++))
    else
        echo -e "${RED}stdout nieprawidlowe${NOCOLOR}"
    fi
}

temp_out=$(mktemp)
trap 'rm -f "temp_out"' INT TERM HUP EXIT

traverse_folder "$tests"

echo -e "Poprawne \e[1m$correct\e[0m na \e[1m$total\e[0m testów"

if  [[ $correct == "$total" ]]; then
  echo -e "\e[1;92mWszystko dobrze! \e[0m"
fi
