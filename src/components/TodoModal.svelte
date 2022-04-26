<script>
    import { fly, fade } from 'svelte/transition';
    import { createEventDispatcher } from "svelte";
    
    // icons
    import EditIcon from './svg/EditIcon.svelte';
    import SaveIcon from './svg/SaveIcon.svelte';
    import CloseIcon from './svg/CloseIcon.svelte';

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
                            <EditIcon />
                        {:else}
                            <SaveIcon />
                        {/if}
                    </button>
                    <button class="todo-modal-close-btn" on:click={() => todoModal = !todoModal}>
                        <CloseIcon />
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
