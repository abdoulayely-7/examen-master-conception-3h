#!/bin/bash
set -e

# Se positionner a la racine du projet quel que soit le dossier d execution
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT_DIR"

source "$HOME/.sdkman/bin/sdkman-init.sh" 2>/dev/null || true
export PATH="$HOME/.sdkman/candidates/java/current/bin:$PATH"

mkdir -p bin
javac -d bin $(find src -name "*.java")
if [ -d "tests" ]; then
    javac -cp bin -d bin $(find tests -name "*.java")
fi
echo "Compilation reussie."