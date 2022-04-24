<script>
    import { onMount } from "svelte";
    import { fly } from "svelte/transition";
    import Clipboard from "svelte-clipboard";
    import Toast from "../stores/toast";

    export let notes = true;
    export let value = '';

    onMount(() => {
        if(localStorage.getItem('notes')) {
            value = localStorage.getItem('notes');
        }
    });

    const saveNotes = () => {
        localStorage.setItem('notes', value);
    };

    const clearText = () => {
        value = '';
        localStorage.removeItem("notes");
    };
</script>

{#if notes}
    <Clipboard text={value} let:copy>
        <div class="main-section" in:fly={{ x:2000, duration: 300 }} out:fly={{ x:3000, duration: 1200 }}>
            <div class="notes-header">
                <button class="menu-btn" title="Clear Text" on:click={() => {
                    clearText(),
                    Toast.warning('Text cleared successfully');
                }}>
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash-2">
                        <polyline points="3 6 5 6 21 6"></polyline>
                        <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                        <line x1="10" y1="11" x2="10" y2="17"></line>
                        <line x1="14" y1="11" x2="14" y2="17"></line>
                    </svg>
                </button>

                <button class="menu-btn" title="Copy Text to Clipboard" on:click={() => {
                    copy(),
                    Toast.success('Text copied successfully');
                }}>
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"></path>
                        <rect x="8" y="2" width="8" height="4" rx="1" ry="1"></rect>
                    </svg>
                </button>
                
                <button class="menu-btn" title="Close Notes" on:click={() => { notes = !notes }}>
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <line x1="18" y1="6" x2="6" y2="18"></line>
                        <line x1="6" y1="6" x2="18" y2="18"></line>
                    </svg>
                </button>
            </div>
            <textarea class="todo-text-area" bind:value on:keyup={saveNotes}/>
        </div>
    </Clipboard>
{/if}

<style>
    .main-section {
        width: 100%;
        height: 100%;
        z-index: 9999;
        inset: 0;
        position: absolute;
        background-color: var(--primary-color);
        padding: 16px;
    }

    .notes-header {
        width: calc(100% - 34px);
        margin-bottom: 16px;
        gap: 16px;
        display: flex;
        align-items: center;
        justify-content: flex-end;
    }

    .menu-btn {
        height: 36px;
        width: 36px;
        padding: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 8px;
        border: 2px solid var(--dark-color);
        background-color: var(--light-color);
    }

    .menu-btn:hover {
        transform: translate(4px,-4px);
        box-shadow: -4px 4px 0 var(--dark-color);
    }

    .todo-text-area {
        width: calc(100% - 68px);
        height: calc(100% - 122px);
        overflow-y: auto;
        padding: 16px;
        border-radius: 12px;
        border: 2px solid var(--dark-color);
        background-color: var(--light-color);
        font-size: 16px;
        font-weight: 800;
        font-family: 'Roboto Mono', monospace;
        resize: none;
    }
</style>
