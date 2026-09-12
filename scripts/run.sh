#!/bin/bash
set -e

# Se positionner a la racine du projet quel que soit le dossier d execution
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT_DIR"

source "$HOME/.sdkman/bin/sdkman-init.sh" 2>/dev/null || true
export PATH="$HOME/.sdkman/candidates/java/current/bin:$PATH"

if [ ! -d "bin/boutique/app" ]; then
    echo "Dossier bin absent ou incomplet, compilation automatique prealable..."
    ./scripts/compile.sh
fi

java -cp bin boutique.app.Main