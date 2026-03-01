<p align="center">
  <img src="src/main/resources/META-INF/pluginIcon.svg" alt="Composable Selection" width="200" />
</p>
<h1 align="center">Composable Selection</h1>

<p align="center">
  <strong>One shortcut. Instant function selection. Works on any Kotlin call.</strong><br>
  <strong>Nested Compose layouts? DSL builders? Select the whole call in one keystroke.</strong>
</p>

<p align="center">
  <a href="https://github.com/dungngminh/composable_selection/actions/workflows/build.yml"><img src="https://github.com/dungngminh/composable_selection/workflows/Build/badge.svg" alt="Build" /></a>
  <a href="https://plugins.jetbrains.com/plugin/29376-composable-selection"><img src="https://img.shields.io/jetbrains/plugin/v/29376.svg" alt="Version" /></a>
  <a href="https://plugins.jetbrains.com/plugin/29376-composable-selection"><img src="https://img.shields.io/jetbrains/plugin/d/29376.svg" alt="Downloads" /></a>
</p>

<!-- Plugin description -->
A lightweight IntelliJ plugin that selects the nearest function call at your cursor. Press again to expand to the parent call — effortless navigation through nested Kotlin function hierarchies.
<!-- Plugin description end -->

```
Ctrl+Alt+W · Any function call · Composables · DSL builders · Expand call-by-call · Keyboard + Context Action
```

### Demo

[![Watch the demo](https://img.youtube.com/vi/aJfMaER2E-s/maxresdefault.jpg)](https://youtu.be/aJfMaER2E-s)

> Click the image to watch the demo on YouTube.

### Features

- **Instant Selection:** One keystroke selects the nearest function call — no dragging, no multi-step expand.
- **Works on Everything:** Jetpack Compose composables, Kotlin DSL builders, regular function calls — if it's a `KtCallExpression`, it's selectable.
- **Two Ways to Trigger:** Keyboard shortcut (`Ctrl+Alt+W`) or context action (`Alt+Enter` > "Select Composable").

### Why Composable Selection

- **Manual selection is slow:** Deeply nested Compose layouts mean scrolling and dragging across dozens of lines just to highlight one composable.
- **No existing tool does this:** IntelliJ has no built-in "select enclosing function call" action. This plugin fills that gap.

| | Without | With Composable Selection |
|---|---|---|
| **Select a call** | Drag from name to closing brace | Press `Ctrl+Alt+W` once |
| **Expand to parent** | Re-drag from parent's name | Press `Ctrl+Alt+W` again |
| **Nested layouts** | Lose your place constantly | Walk up call-by-call |

### Usage

| Platform | Shortcut |
|---|---|
| Windows / Linux | `Ctrl` + `Alt` + `W` |
| macOS | `Control` + `Option` + `W` |

1. Place your cursor inside any function call.
2. Press the shortcut — the **nearest function call** is selected.
3. Press again — selection **expands to the parent call**.
4. Keep pressing to walk up the call hierarchy.

**Context action:** `Alt` + `Enter` > **"Select Composable"**

**Menu:** **Edit** > **Select Composable** (bottom of menu)

### Installation

**From IDE (recommended):**

`Settings` > `Plugins` > `Marketplace` > Search **"Composable Selection"** > `Install`

**From JetBrains Marketplace:**

Go to [Composable Selection](https://plugins.jetbrains.com/plugin/29376-composable-selection) and click `Install to ...`.

**Manual:**

Download the [latest release](https://github.com/dungngminh/composable_selection/releases/latest) and install via `Settings` > `Plugins` > `Settings icon` > `Install plugin from disk...`

### Compatibility

| Requirement | Version              |
|---|----------------------|
| IntelliJ Platform | from 2024.2|
| Kotlin Plugin | Required             |

### Contributing

```bash
git clone https://github.com/<your-username>/composable_selection.git
cd composable_selection
./gradlew runIde    # Run plugin in sandbox IDE
./gradlew test      # Run tests
```

1. Fork this repository.
2. Create a branch, make your changes.
3. Run tests, open a Pull Request.

### License

MIT — see [LICENSE](LICENSE)

---
