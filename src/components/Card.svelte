<script>
	import OutClick from 'svelte-outclick';
    import { createEventDispatcher } from "svelte";
    
    const dispatch = createEventDispatcher();
    export let menu = false;
    export let todoStatus = false;

    const doneButton = () => {
        dispatch('done');
    }

    const deleteButton = () => {
        dispatch('delete');
    }
</script>

<OutClick on:outclick={() => menu = false}>
    <div class="main-section">
        <div class="card" class:todo-done={todoStatus}>
            <div class="todo-text">
                <slot/>
            </div>
            <button class="menu-btn" on:click={() => menu =!menu}>
                {#if todoStatus}
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <polyline points="20 6 9 17 4 12"></polyline>
                    </svg>
                {:else}              
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <circle cx="12" cy="12" r="1"></circle><circle cx="12" cy="5" r="1"> </circle>
                        <circle cx="12" cy="19" r="1"></circle>
                    </svg>
                {/if}
            </button>
        </div>
        
        {#if menu}
            <div class="menu">
                <button class="done-btn" on:click={doneButton}>
                    {todoStatus ? 'Undone' : 'Done'}
                </button>
                <button class="delete-btn" on:click={deleteButton}>
                    Delete
                </button>
            </div>
        {/if}
    </div>
</OutClick>

<style>
    .main-section {
        position: relative;
        margin-bottom: 16px;
    }

    .card {
        padding: 16px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        border: 2px solid var(--dark-color);
        background-color: var(--primary-color);
    }

    .todo-text {
        font-size: 16px;
        font-weight: 800;
        font-family: 'Roboto Mono', monospace;
    }

    .menu-btn {
        width: 32px;
        height: 32px;
        display: flex;
        padding: 2px;
        background-color: var(--warning-color);
        border: 2px solid var(--dark-color);
        cursor: pointer;
    }

    .menu {
        width: 74px;
        margin-top: 16px;
        border: 2px solid var(--dark-color);
        background-color: var(--primary-color);
        position: absolute;
        right: 0;
        padding: 0 8px 8px 8px;
        z-index: 1111;
    }

    .menu::before {
        content: "";
        position: absolute;
        display: block;
        width: 0;
        left: 50%;
        bottom: 86px;
        border: 9px solid transparent;
        border-top: 0;
        border-bottom: 12px solid var(--dark-color);
        transform: translate(-50%, calc(100% + -4px));
    }

    .menu button {
        width: 100%;
        height: 26px;
        border: 2px solid var(--dark-color);
        border-radius: 0;
        padding: 0;
        cursor: pointer;
        margin-top: 8px;
    }

    .done-btn {
        background-color: var(--success-color);
    }

    .delete-btn {
        background-color: var(--danger-color);
    }

    .menu button:hover {
        transform: translate(3px,-3px);
        box-shadow: -3px 3px 0 var(--dark-color);
    }

    .todo-done {
        background-color: var(--success-color);
    }
</style>