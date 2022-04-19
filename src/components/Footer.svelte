<script>
    import { createEventDispatcher } from "svelte";
    const dispatch = createEventDispatcher();

    export let todoItem = "";

    const addToList = () => {
        dispatch('todo');
    }
</script>

<form on:submit|preventDefault={addToList}>
    <input class="br-8" type="search" bind:value={todoItem} placeholder="Add todo.." autofocus>
    {#if todoItem}
        <button class="add-btn br-8" type="submit">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
        </button>
    {/if}
</form>

<style>
    form {
        height: 56px;
        padding: 0 16px;
        border-top: 2px solid var(--dark-color);
        background-color: var(--primary-color);
        display: flex;
        gap: 16px;
        align-items: center;
        justify-content: space-between;
    }

    input[type="search"] {
        font-size: 16px;
        padding: 0px 8px 0 8px;
        height: 38px;
        width: 100%;
        border: 2px solid var(--dark-color);
        font-weight: 500;
        font-family: 'Roboto Mono', monospace;
    }

    input[type="search"]:focus {
        outline: none;
    }

    .add-btn {
        height: 38px;
        width: 44px;
        padding: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        border: 2px solid var(--dark-color);
        background-color: var(--light-color);
    }

    .add-btn:hover {
        transform: translate(4px,-4px);
        box-shadow: -4px 4px 0 var(--dark-color);
    }
</style>