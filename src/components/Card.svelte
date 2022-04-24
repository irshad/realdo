<script>
	import OutClick from 'svelte-outclick';
    import { createEventDispatcher } from "svelte";
    import { Haptics, ImpactStyle  } from '@capacitor/haptics';

    const dispatch = createEventDispatcher();
    export let menu = false;
    export let todoStatus = false;
    export let text = '';
    export let search = false;

    const hapticsVibrate = async () => {
        await Haptics.impact({ style: ImpactStyle.Medium });
    };

    const doneButton = () => {
        dispatch('done');
        menu = false;
        hapticsVibrate();
    }

    const deleteButton = () => {
        dispatch('delete');
        menu = false;
        hapticsVibrate();
    }
</script>

<div class="main-section">
    <div class="card {todoStatus == false ? search == true ? 'search-undone' : '' : ''}" class:todo-done={todoStatus}>
        <div class="todo-text-parent" title={text} on:click>
            <div class="todo-text">
                {@html text}
            </div>
        </div>
        <button class="menu-btn br-8" on:click={() => menu =!menu}>
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

    <OutClick on:outclick={() => menu = false}>
        {#if menu}
            <div class="menu">
                <button class="done-btn br-8" on:click={doneButton}>
                    {todoStatus ? 'Undone' : 'Done'}
                </button>
                <button class="delete-btn br-8" on:click={deleteButton}>
                    Delete
                </button>

                <span class="menu-arrow">
                    <svg width="32" height="20" viewBox="-2 -2 37 28" fill="none" xmlns="http://www.w3.org/2000/svg" style="top: -18px; transform: scale(-1, 1); right: 25px;">
                        <g>
                            <path d="M0.138981 4.44226C0.0834319 1.0678 3.97439 -0.854559 6.62079 1.23988L35.9246 24.4317C37.4112 25.6083 36.5792 28 34.6834 28L2.49412 28C1.40238 28 0.512363 27.1245 0.494394 26.0329L0.138981 4.44226Z" fill="var(--primary-color)" stroke="#000000" stroke-width="2.5" mask="url(#path-1-inside-1_0_1)" class="arrow-grey"></path>
                        </g>
                    </svg>
                </span>
            </div>
        {/if}
    </OutClick>
</div>

<style>
    .main-section {
        position: relative;
        margin-bottom: 16px;
    }

    .card {
        height: 62px;
        padding: 0 16px;
        gap: 8px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        border-radius: 14px;
        border: 2px solid var(--dark-color);
        background-color: var(--primary-color);
        cursor: pointer;
    }

    .todo-text-parent {
        height: 100%;
        width: 84%;
        display: flex;
        align-items: center;
    }

    .todo-text {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
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

    .menu-btn:hover {
        transform: translate(4px,-4px);
        box-shadow: -4px 4px 0 var(--dark-color);
    }

    .menu {
        width: 74px;
        margin-top: 24px;
        border: 2px solid var(--dark-color);
        background-color: var(--primary-color);
        position: absolute;
        right: 0;
        padding: 0 8px 8px 8px;
        z-index: 1111;
        border-radius: 14px;
        box-shadow: 0px 5px 15px 0px rgba(0,0,0,0.3);
    }

    .menu-arrow {
        right: 25px;
        top: -20px;
        position: absolute;
    }

    .menu button {
        width: 100%;
        height: 30px;
        border: 2px solid var(--dark-color);
        padding: 0;
        cursor: pointer;
        margin-top: 8px;
        font-weight: 800;
        font-family: 'Roboto Mono', monospace;
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

    .search-undone {
        background-color: var(--light-color);
    }
</style>
