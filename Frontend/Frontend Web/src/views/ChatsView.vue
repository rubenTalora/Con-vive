<template>
  <div class="chats-view">
    <header class="page-header">
      <h1>💬 Chats</h1>
      <p class="page-subtitle">Conversaciones con posibles compañeros</p>
    </header>

    <div class="chats-container">
      <!-- Lista de chats -->
      <div class="chats-list">
        <div class="search-chat">
          <input 
            type="text" 
            v-model="searchQuery"
            placeholder="Buscar conversación..."
            class="search-input"
          />
        </div>

        <div class="chat-items">
          <div 
            v-for="chat in mockChats" 
            :key="chat.id"
            class="chat-item"
            :class="{ active: selectedChat?.id === chat.id, unread: chat.noLeidos > 0 }"
            @click="selectChat(chat)"
          >
            <div class="chat-avatar" :style="{ background: chat.color }">
              {{ chat.iniciales }}
            </div>
            <div class="chat-info">
              <div class="chat-header-info">
                <h4 class="chat-name">{{ chat.nombre }}</h4>
                <span class="chat-time">{{ chat.hora }}</span>
              </div>
              <div class="chat-preview">
                <p class="last-message">{{ chat.ultimoMensaje }}</p>
                <span v-if="chat.noLeidos > 0" class="unread-badge">
                  {{ chat.noLeidos }}
                </span>
              </div>
            </div>
          </div>
        </div>

        <div v-if="mockChats.length === 0" class="empty-chats">
          <div class="empty-icon">💬</div>
          <p>No tienes conversaciones aún</p>
        </div>
      </div>

      <!-- Área de conversación -->
      <div class="chat-area">
        <div v-if="selectedChat" class="chat-content">
          <!-- Header del chat -->
          <div class="chat-header">
            <div class="chat-user">
              <div class="chat-avatar-large" :style="{ background: selectedChat.color }">
                {{ selectedChat.iniciales }}
              </div>
              <div class="chat-user-info">
                <h3>{{ selectedChat.nombre }}</h3>
                <p class="status">{{ selectedChat.online ? '🟢 En línea' : '⚪ Desconectado' }}</p>
              </div>
            </div>
            <button class="btn-options">⋮</button>
          </div>

          <!-- Mensajes -->
          <div class="messages-container">
            <div 
              v-for="mensaje in selectedChat.mensajes" 
              :key="mensaje.id"
              class="message"
              :class="{ sent: mensaje.enviado, received: !mensaje.enviado }"
            >
              <div class="message-bubble">
                <p>{{ mensaje.texto }}</p>
                <span class="message-time">{{ mensaje.hora }}</span>
              </div>
            </div>
          </div>

          <!-- Input de mensaje -->
          <div class="message-input-container">
            <input 
              v-model="newMessage"
              type="text" 
              placeholder="Escribe un mensaje..."
              class="message-input"
              @keyup.enter="sendMessage"
            />
            <button @click="sendMessage" class="btn-send">
              📤
            </button>
          </div>
        </div>

        <div v-else class="no-chat-selected">
          <div class="no-chat-icon">💬</div>
          <h3>Selecciona un chat</h3>
          <p>Elige una conversación para empezar a chatear</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const searchQuery = ref('')
const selectedChat = ref(null)
const newMessage = ref('')

// Mock data - Datos de ejemplo
const mockChats = ref([
  {
    id: 1,
    nombre: 'Ana García',
    iniciales: 'AG',
    color: '#667eea',
    ultimoMensaje: '¿Cuándo podemos ver el piso?',
    hora: '10:30',
    noLeidos: 1,
    online: true,
    mensajes: [
      { id: 1, texto: 'Hola! Vi tu anuncio del piso', enviado: false, hora: '10:15' },
      { id: 2, texto: 'Hola Ana! Sí, pasa ig', enviado: true, hora: '10:20' },
      { id: 3, texto: '¿Cuándo podemos ver el piso?', enviado: false, hora: '10:30' }
    ]
  },
  {
    id: 2,
    nombre: 'Carlos Martínez',
    iniciales: 'CM',
    color: '#f59e0b',
    ultimoMensaje: 'Perfecto, nos vemos entonces',
    hora: 'Ayer',
    noLeidos: 0,
    online: false,
    mensajes: [
      { id: 1, texto: 'Hola! Estoy interesado en la habitación', enviado: false, hora: 'Ayer 14:00' },
      { id: 2, texto: 'Genial! ¿Quieres quedar para verla?', enviado: true, hora: 'Ayer 14:15' },
      { id: 3, texto: 'Perfecto, nos vemos entonces', enviado: false, hora: 'Ayer 14:20' }
    ]
  },
  {
    id: 3,
    nombre: 'Laura Sánchez',
    iniciales: 'LS',
    color: '#10b981',
    ultimoMensaje: 'Muchas gracias por la info',
    hora: '12/02',
    noLeidos: 0,
    online: false,
    mensajes: [
      { id: 1, texto: '¿Cuál es el precio final con gastos?', enviado: false, hora: '12/02 11:00' },
      { id: 2, texto: '450€ + unos 50€ de gastos aprox', enviado: true, hora: '12/02 11:30' },
      { id: 3, texto: 'Muchas gracias por la info', enviado: false, hora: '12/02 12:00' }
    ]
  },
  {
    id: 4,
    nombre: 'Miguel Torres',
    iniciales: 'MT',
    color: '#ef4444',
    ultimoMensaje: '¿Aceptáis mascotas?',
    hora: '10/02',
    noLeidos: 1,
    online: false,
    mensajes: [
      { id: 1, texto: 'Hola! Me interesa vuestra habitación', enviado: false, hora: '10/02 09:00' },
      { id: 2, texto: '¿Aceptáis mascotas?', enviado: false, hora: '10/02 09:05' }
    ]
  }
])

const selectChat = (chat) => {
  selectedChat.value = chat
  chat.noLeidos = 0
}

const sendMessage = () => {
  if (!newMessage.value.trim() || !selectedChat.value) return
  
  const mensaje = {
    id: selectedChat.value.mensajes.length + 1,
    texto: newMessage.value,
    enviado: true,
    hora: new Date().toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' })
  }
  
  selectedChat.value.mensajes.push(mensaje)
  selectedChat.value.ultimoMensaje = newMessage.value
  newMessage.value = ''
}
</script>

<style scoped>
.chats-view {
  padding: 2rem;
  max-width: 1400px;
  margin: 0 auto;
  height: calc(100vh - 4rem);
  display: flex;
  flex-direction: column;
}

.page-header {
  margin-bottom: 1.5rem;
  flex-shrink: 0;
}

.page-header h1 {
  font-size: 2rem;
  color: #1f2937;
  margin-bottom: 0.5rem;
}

.page-subtitle {
  color: #6b7280;
  font-size: 1.1rem;
}

.chats-container {
  display: grid;
  grid-template-columns: 350px 1fr;
  gap: 1.5rem;
  background: white;
  border-radius: 1rem;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  flex: 1;
  min-height: 0;
}

/* Lista de chats */
.chats-list {
  border-right: 1px solid #e5e7eb;
  display: flex;
  flex-direction: column;
}

.search-chat {
  padding: 1rem;
  border-bottom: 1px solid #e5e7eb;
  flex-shrink: 0;
}

.search-input {
  width: 100%;
  padding: 0.75rem;
  border: 2px solid #e5e7eb;
  border-radius: 0.5rem;
  font-size: 0.9rem;
}

.search-input:focus {
  outline: none;
  border-color: #667eea;
}

.chat-items {
  overflow-y: auto;
  flex: 1;
}

.chat-item {
  padding: 1rem;
  display: flex;
  gap: 1rem;
  cursor: pointer;
  transition: background 0.3s;
  border-left: 3px solid transparent;
}

.chat-item:hover {
  background: #f9fafb;
}

.chat-item.active {
  background: #ede9fe;
  border-left-color: #667eea;
}

.chat-item.unread {
  background: #fef3c7;
}

.chat-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 700;
  flex-shrink: 0;
}

.chat-info {
  flex: 1;
  min-width: 0;
}

.chat-header-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.25rem;
}

.chat-name {
  font-size: 1rem;
  margin: 0;
  color: #1f2937;
}

.chat-time {
  font-size: 0.75rem;
  color: #9ca3af;
}

.chat-preview {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.last-message {
  margin: 0;
  font-size: 0.875rem;
  color: #6b7280;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  flex: 1;
}

.unread-badge {
  background: #667eea;
  color: white;
  padding: 0.125rem 0.5rem;
  border-radius: 1rem;
  font-size: 0.75rem;
  font-weight: 600;
  min-width: 20px;
  text-align: center;
}

.empty-chats {
  text-align: center;
  padding: 3rem 1rem;
  color: #9ca3af;
}

.empty-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

/* Área de chat */
.chat-area {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.chat-content {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.chat-header {
  padding: 1.5rem;
  border-bottom: 1px solid #e5e7eb;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-shrink: 0;
}

.chat-user {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.chat-avatar-large {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 700;
  font-size: 1.25rem;
}

.chat-user-info h3 {
  margin: 0 0 0.25rem 0;
  color: #1f2937;
}

.status {
  margin: 0;
  font-size: 0.875rem;
  color: #6b7280;
}

.btn-options {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #6b7280;
  padding: 0.5rem;
}

.messages-container {
  flex: 1;
  overflow-y: auto;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.message {
  display: flex;
}

.message.sent {
  justify-content: flex-end;
}

.message.received {
  justify-content: flex-start;
}

.message-bubble {
  max-width: 70%;
  padding: 0.75rem 1rem;
  border-radius: 1rem;
}

.message.sent .message-bubble {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-bottom-right-radius: 0.25rem;
}

.message.received .message-bubble {
  background: #f3f4f6;
  color: #1f2937;
  border-bottom-left-radius: 0.25rem;
}

.message-bubble p {
  margin: 0 0 0.25rem 0;
}

.message-time {
  font-size: 0.75rem;
  opacity: 0.7;
}

.message-input-container {
  padding: 1.5rem;
  border-top: 1px solid #e5e7eb;
  display: flex;
  gap: 1rem;
  flex-shrink: 0;
}

.message-input {
  flex: 1;
  padding: 0.75rem 1rem;
  border: 2px solid #e5e7eb;
  border-radius: 2rem;
  font-size: 1rem;
}

.message-input:focus {
  outline: none;
  border-color: #667eea;
}

.btn-send {
  width: 48px;
  height: 48px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 50%;
  cursor: pointer;
  font-size: 1.25rem;
  transition: transform 0.2s;
}

.btn-send:hover {
  transform: scale(1.1);
}

.no-chat-selected {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #9ca3af;
}

.no-chat-icon {
  font-size: 4rem;
  margin-bottom: 1rem;
}

.no-chat-selected h3 {
  color: #6b7280;
  margin-bottom: 0.5rem;
}

/* Responsive */
@media (max-width: 768px) {
  .chats-view {
    padding: 1rem;
  }

  .chats-container {
    grid-template-columns: 1fr;
  }

  .chats-list {
    display: none;
  }

  .chat-area {
    display: block;
  }
}
</style>
