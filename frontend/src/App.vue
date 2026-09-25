<script setup>
import { onMounted, ref } from 'vue'

const running = ref(false)
const elapsed = ref(0)
const history = ref([])

let startTime = 0
let interval = null

function startTimer() {
  if (running.value) return

  running.value = true
  startTime = Date.now() - elapsed.value

  interval = setInterval(() => {
    elapsed.value = Date.now() - startTime
  }, 10)
}

async function stopTimer() {
  if (!running.value) return

  running.value = false
  clearInterval(interval)

  await fetch('/api/times', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      durationMs: elapsed.value,
    }),
  })

  elapsed.value = 0

  await loadHistory()
}

function resetTimer() {
  running.value = false
  clearInterval(interval)
  elapsed.value = 0
}

async function loadHistory() {
  const response = await fetch('/api/times')
  history.value = await response.json()
}

async function clearHistory() {
  await fetch('/api/times', {
    method: 'DELETE',
  })

  await loadHistory()
}

function formatTime(milliseconds) {
  const minutes = Math.floor(milliseconds / 60000)
  const seconds = Math.floor((milliseconds % 60000) / 1000)
  const millis = Math.floor(milliseconds % 1000)

  return (
    String(minutes).padStart(2, '0') +
    ':' +
    String(seconds).padStart(2, '0') +
    '.' +
    String(millis).padStart(3, '0')
  )
}

onMounted(loadHistory)
</script>

<template>
  <main class="container">
    <h1>Timer App</h1>

    <div class="timer">
      {{ formatTime(elapsed) }}
    </div>

    <div class="buttons">
      <button
        @click="startTimer"
        :disabled="running"
      >
        Start
      </button>

      <button
        @click="stopTimer"
        :disabled="!running"
      >
        Stop
      </button>

      <button @click="resetTimer">
        Reset
      </button>
    </div>

    <section class="history">
      <h2>Verlauf</h2>

      <button
        v-if="history.length > 0"
        @click="clearHistory"
      >
        Verlauf löschen
      </button>

      <p v-if="history.length === 0">
        Noch keine Zeiten vorhanden.
      </p>

      <ul v-else>
        <li
          v-for="entry in history"
          :key="entry.id"
        >
          {{ formatTime(entry.durationMs) }}
          -
          {{ new Date(entry.createdAt).toLocaleString() }}
        </li>
      </ul>
    </section>
  </main>
</template>

<style scoped>
.container {
  max-width: 600px;
  margin: 80px auto;
  padding: 20px;
  text-align: center;
  font-family: Arial, sans-serif;
}

.timer {
  margin: 40px 0;
  font-family: monospace;
  font-size: 64px;
}

.buttons {
  display: flex;
  justify-content: center;
  gap: 12px;
}

button {
  padding: 10px 24px;
  font-size: 16px;
  cursor: pointer;
}

button:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}

.history {
  margin-top: 50px;
}

.history ul {
  list-style: none;
  padding: 0;
  margin-top: 20px;
}

.history li {
  padding: 10px;
  border-bottom: 1px solid #ddd;
}
</style>
