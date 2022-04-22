<script>
    import { fly, fade } from 'svelte/transition';
    import { createEventDispatcher } from "svelte";

    const dispatch = createEventDispatcher();

    export let todoModal = false;
    export let todoModalID = '';
    export let todoModalText = '';
    export let todoModalStatus = '';
    let edit = true;

    console.log(todoModalID);

    const updateTodo = () => {
        dispatch('updateTodo');
    };
</script>

{#if todoModal}
    <div class="todo-modal" transition:fade>
        <div class="todo-modal-container" transition:fly={{y: 250}}>
            <div class="todo-modal-header">
                <div class="todo-status">
                    Status: {todoModalStatus ? 'Done' : 'Not Done'}
                </div>
                <div class="modal-btn-section">
                    <button class="todo-modal-close-btn" on:click={() => edit=!edit}>
                        {#if edit}
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M12 20h9"></path>
                                <path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"></path>
                            </svg>
                        {:else}
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"></path>
                                <polyline points="17 21 17 13 7 13 7 21"></polyline><polyline points="7 3 7 8 15 8"></polyline>
                            </svg>
                        {/if}
                    </button>
                    <button class="todo-modal-close-btn" on:click={() => todoModal = !todoModal}>
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <line x1="18" y1="6" x2="6" y2="18"></line>
                            <line x1="6" y1="6" x2="18" y2="18"></line>
                        </svg>
                    </button>
                </div>
            </div>

            <textarea class="todo-text-area" disabled={edit} bind:value={todoModalText} on:keyup={updateTodo}/>
        </div>
    </div>
{/if}

<style>
    .todo-modal {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        display: block;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 10;
        cursor: pointer;
    }

    .todo-modal-container {
        height: 300px;
        width: calc(100% - 32px);
        padding: 16px;
        bottom: 0;
        position: absolute;
        border-top: 2px solid var(--dark-color);
        background-color: var(--primary-color);
    }

    .todo-modal-header {
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .todo-status {
        font-size: 16px;
        font-weight: 800;
        font-family: 'Roboto Mono', monospace;
    }

    .todo-modal-close-btn {
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

    .todo-modal-close-btn:hover {
        transform: translate(4px,-4px);
        box-shadow: -4px 4px 0 var(--dark-color);
    }

    .todo-text-area {
        width: calc(100% - 32px);
        height: calc(100% - 90px);
        overflow-y: auto;
        padding: 16px;
        margin-top: 16px;
        border-radius: 12px;
        border: 2px solid var(--dark-color);
        background-color: var(--light-color);
        font-size: 16px;
        font-weight: 800;
        font-family: 'Roboto Mono', monospace;
        resize: none;
    }

    .modal-btn-section {
        display: flex;
        gap: 16px;
    }

    /* textarea:disabled {} */
</style>
