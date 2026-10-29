#!/bin/bash
# Kali_Aishah_system_report.sh — system monitoring report (DevOps midterm, Kali Aishah, ID 37830)
# Usage: ./scripts/Kali_Aishah_system_report.sh ["Full Name"]

STUDENT_NAME="${1:-Kali Aishah}"
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
LOG_FILE="$SCRIPT_DIR/../logs/Kali_Aishah_system_report.log"

{
  echo "=================================================="
  echo " SYSTEM REPORT — Student: $STUDENT_NAME"
  echo "=================================================="
  echo "Date and time : $(date '+%Y-%m-%d %H:%M:%S')"
  echo "Username      : $(whoami)"
  echo "OS            : $(grep PRETTY_NAME /etc/os-release | cut -d= -f2 | tr -d '"')"
  echo "Kernel        : $(uname -r)"
  echo
  echo "--- CPU ---"
  lscpu | grep -E '^Architecture|^CPU\(s\)|Model name'
  echo
  echo "--- Memory ---"
  free -h
  echo
  echo "--- Disk usage ---"
  df -h /
  echo
  echo "--- Available disk space ---"
  df -h / | awk 'NR==2 {print "Free on /: " $4 " of " $2 " (used " $5 ")"}'
  echo
} | tee -a "$LOG_FILE"

echo "Report saved to: $LOG_FILE"
