;**
;**	Logics for room 550 -- beside the Raging River
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, C. Escobar, and Roger Hardy
;**
;**	Last Update:	January 22, 1990
;**


(script#				550)

(public
	rm550				0
)


(define	nearLog					(< (ego distanceTo aLog) 25))
(define	onLand					(&	(ego onControl       ) cGreen))
(define	onShallowWater	 		(& (ego onControl origin) cBlue))
(define	onDeepWater		 		(& (ego onControl origin) cCyan))
(define	inTrouble				(&	(ego onControl origin) cRed))


(define	vRoom							550)
	(define	lLog					0)

(define	vPswimming					551)

(define	vPwading						552)

(define	vPmountingLog				553)
	(define	lPullingLog			0)
	(define	lMountingLog		1)
	(define	lFloatingEast		2)
	(define	lFloatingNortheast 3)
	(define	lTheLog				4)

;(define	vPdrowning					812)
	(define	lDrowningLarge		0)
	(define	lDrowningSmall		2)


;(define	sNearDanger					006)	
;(define	sRoaringRapids				550)


(define	logX							077)
(define	logY							155)
(define	logXmiddle					170)
(define	logYmiddle					131)
(define	deltaX						 20)
(define	deltaY						-14)


(enum												;**	RoomScript state values
	intro
	dragLog
	draggingLog
	doneDragging
	mountLog
	atLog
	onLog
	boyWhatFun
	leaveLog
	offLog
	shesOff
	youreMoving
	floatingOffScreen
	changeRooms
	startDrowning
	drowningSmall
	allDead
)


(local
	drowningLoop
	[string  55] 
	[string2 22]
)

(enum	550
	ON_LOG
	PULLING_LOG
)


(instance rm550 of Room
	(properties
		picture		550
		horizon		50
		south			540
	)


	(method (init)
		(Load VIEW vPswimming)
		(Load VIEW vPwading)
		(Load VIEW vPmountingLog)

		(Load SOUND sNearDanger)

		(super init)

		(aLog	 				
			setCycle			Forward,
			init,
		)

		(self
			setScript 		RoomScript
		)


		;**	
		;**	Remove this debug code later:
		;**	

		(if debugging
								(= egoIsPatti TRUE)
								(= normalEgoView	vPshortDress)
								((Inventory at iDress) view ivDressShort)
								(= egoState NORMAL)
		)

		;**	
		;**	end of debug code 
		;**	


		(NormalEgo)
		(ego
			posn				159 188,
			loop				facingUp,
			init,
			viewer			PattiViewer,
		)
	)
)


(instance RoomScript of Script
	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'mount, ride, (climb, sit, get<on)/log')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not nearLog)
						(NotClose)
					)
					(else
					  	(self changeState mountLog)
					)
				)
			)

			((Said 'rise, leave, (get<off), (climb, get<off, up), (get<up), (stand<up)')
				(cond
					((!= egoState ON_LOG)
						(Print "How can you do that?")
					)
					(else
						(self changeState leaveLog)
					)
				)
			)

			((Said 'weave')
				(Print "That was Larry's bit, Patti!")
			)

			((Said '/island')
				(Print "There it is! Right over there! Just across the channel!")
				(Print "(Why don't you try swimming over there?
					Hee, hee.)" LOW) 
			)

			((Said 'hit, kick, hold, grab/log')
				(cond
					((not nearLog)
						(NotClose)
					)
					((== egoState ON_LOG)
						(Print "Whee! Giddyup, old Log!")
					)
					(else
						(Print "That is not the answer, 
							but the log is the key to your success here.")
					)
				)
			)

			((Said 'pull, drag, use/log')
				(cond
					((not nearLog)
						(NotClose)
					)
					((== egoState ON_LOG)
						(Print "A good idea, but difficult to do when you are
							sitting smack dab on top of your log!")
					)
					((!= (aLog x) logX)		;**	log's already in middle
						(Print "If you pull it any farther,
							you'll lose it in the current.")
					)
					(else
						(self changeState dragLog)
					)
				)
			)

			((Said 'drink')
				(Print "You're not really that thirsty.")
			)

			((or	(Said 'enter, leave, exit, (get<in)/water')
					(Said 'swim, wade'))
				(Print "You'll have to get wet first.")
			)

			((Said 'get>')
				(cond
					((Said '/log')
						(Print "Yeah; just what you need: a big hunk of timber!
							You don't need to TAKE the log...")
					)
					((Said '/tree')
						(Print "How can you reach them from here?")
					)
					((Said '/plant')
						(Print "The plants will do you no good.")
					)
				)
			)

			((Said 'look<in/plant')
				(Print "Look! A small log rests in the weeds
					near the riverbank.")
			)

			((Said 'look>')
				(cond
					((Said '/log')
						(cond
							((!= (aLog x) logX)
								(Print "You have dragged a log into the middle
									of the river.
									Now what are you going to do with it?")
							)
							((== (ego view) normalEgoView)
								(Print "Where? 
									You see no log from your current location.")
								(Print "(Here's one place where you may have	
									to get your feet wet, Patti!)" LOW)
							)
							(else
								(Print "Look! A small log rests in the weeds
									near the riverbank.")
							)
						)
					)

					((Said '/plant')
						(Print "Dutch rush grows in the shallow water at the
							edge of the stream.")
					)

					((Said '/tree')
						(Print "The only trees you can see from here are
							all the way across the river, 
							and completely out of your reach.")
					)

					((Said '[/room, water, river]')
						(Print "A roaring river rushes by.
							It looks too deep to wade across
							and too rapid to swim.
							How will you proceed from here?")
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)


			;**	Pull log away from shore

			(dragLog
				(Ok)
				(theGame changeScore 10)
				(HandsOff)
				(= egoState PULLING_LOG)
				(ego
					illegalBits		0,
					ignoreActors,
					setMotion		MoveTo (+ (aLog x) deltaX) (+ (aLog y) deltaY) self,
				)
			)

			(draggingLog
				(aLog					hide)
				(ego
					cycleSpeed		1,
					moveSpeed		1,
					viewer			FALSE,
					posn				(aLog x) (aLog y),
					view				vPmountingLog,
					setLoop			lPullingLog,
					setCycle			Forward,
					setMotion		MoveTo logXmiddle logYmiddle self,
				)
			)

			(doneDragging
				(aLog
					posn				logXmiddle logYmiddle ,
					show,
				)
				(ego
					viewer			PattiViewer,
					posn				(+ (aLog x) deltaX) (+ (aLog y) deltaY),
				)
				(NormalEgo facingDown vPswimming)
				(= egoState NORMAL)
			)


			;**	Here's where she mounts her big log...

			(mountLog
				(Ok)
				(HandsOff)
				(ego
					illegalBits		0,
					ignoreActors,
					setMotion		MoveTo (+ (aLog x) deltaX) (+ (aLog y) deltaY) self,
				)
			)

			(atLog
				(aLog					hide)
				(ego
					viewer			FALSE,
					cycleSpeed		1,
					posn				(aLog x) (aLog y),
					view				vPmountingLog,
					loop				lMountingLog,
					cel				0,
					setCycle			EndLoop self,
				)
			)

			(onLog
				(ego
					setLoop			lFloatingEast,
					setCycle			Forward,
				)
				(= egoState ON_LOG)
				(if (!= (aLog x) logX)
					(self changeState shesOff)
				else
					(delay 7)
				)
				(Print "You mount the gnarly log.")
				(if (>= filthLevel DIRTY)
					(Print "(...an experience not wholly unfamiliar to you!)" LOW)
				)
			)

			(boyWhatFun
				(Print "Boy, is this fun! 
					Sitting on a log, mired in the mud of the river bottom.")
				(User canInput TRUE)
			)

			;**	wait here until they figure out they're not going anywhere.

			(leaveLog
				(Ok)
				(HandsOff)
				(ego
					setLoop			lMountingLog,
					setCel			LastCel,
					setCycle			BegLoop self,
				)
			)

			(offLog
				(= egoState NORMAL)
				(aLog					show)
				(NormalEgo facingDown vPswimming)
				(ego
					viewer			PattiViewer,
					posn				(+ (aLog x) deltaX) (+ (aLog y) deltaY),
				)
			)


			;**	She mounted it after pulling on it

			(shesOff
				(theGame changeScore 20)
				(ego
					cycleSpeed		0,
					setStep			2 1,
					setMotion		MoveTo 278 106 self,
				)
				(= cycles 11)
			)

			(youreMoving
				(Print "You did it!")
				(Print "The current has caught your log and is taking you
					downstream... toward your Larry?")
			)

			(floatingOffScreen
				(ego
					setLoop			lFloatingNortheast,
					setMotion		MoveTo 348 73 self,
				)
			)

			(changeRooms
				(Print "Press F8 to bypass the next scene.")
				(curRoom newRoom 560)
			)


			;**	handle drowning in the current

			(startDrowning
				(HandsOff)
				(= egoState DROWNING)
				(Print (Format @string "%s" expletive) HIGH #dispose)
				(= drowningLoop lDrowningLarge)
				(ego
					viewer			drowningViewer,
					illegalBits		0,
					ignoreActors,
					view				vPdrowning,
					setLoop			drowningLoop,
					setCycle			Forward,
					setMotion		MoveTo (if (> (ego x) 278) (ego x) else 278) 106 self,
				)
				(soundFX
					number			sNearDanger,
					loop				FOREVER,
					play,
				)
			)

			(drowningSmall
				(cls)
				(= drowningLoop lDrowningSmall)
				(ego
					setMotion		MoveTo 348 73 self,
				)
			)

			(allDead
				(theGame setScript (ScriptID DYING))
				((ScriptID DYING) 
					caller		vPdeadDrowning,
					register 	(Format @string "Patti! 
										Be more careful!
										There are treacherous undercurrents in this
										river."),
					next			(Format @string2 "Head over heels but not in love!"),
				)
			)
		)
	)
)


(instance aLog of Prop
	(properties
		view				vRoom
;		loop				lLog
		x					logX
		y					logY
		cycleSpeed		2
		yStep				12
	)
)

(instance PattiViewer of Code
	(method (doit)

		(ego brBottom		(+ (ego y) 1 ))
		(ego brTop			(- (ego brBottom) (ego yStep)))
		(ego brLeft			(- (ego x) 8))
		(ego brRight		(+ (ego x) 8))

		(cond
			(inTrouble
				(RoomScript changeState startDrowning)
			)
			(onLand
				(ego
					view				normalEgoView,
					setCycle			Walk,
				)
			)
			(onShallowWater
				(ego
					view				vPwading,
					setCycle			Walk,
				)
			)
			(onDeepWater
				((Inventory at iDress) view ivDressWet)
				(ego
					view				vPswimming,
					setCycle			Forward,
				)
			)
		)
	)
)

(instance drowningViewer of Code
	(method (doit)
		(if (Random 0 5)
			(ego
				setLoop				(+ drowningLoop (Random 0 1)),
			)
		)
	)
)

